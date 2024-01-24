package kr.co.fastcampus.yanabada.domain.chat.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import kr.co.fastcampus.yanabada.common.exception.CannotNegotiateOwnProductException;
import kr.co.fastcampus.yanabada.common.exception.IncorrectChatRoomMember;
import kr.co.fastcampus.yanabada.common.exception.NegotiationNotPossibleException;
import kr.co.fastcampus.yanabada.domain.chat.dto.ReceivedChatMessage;
import kr.co.fastcampus.yanabada.domain.chat.dto.SendChatMessage;
import kr.co.fastcampus.yanabada.domain.chat.dto.request.ChatRoomModifyRequest;
import kr.co.fastcampus.yanabada.domain.chat.dto.request.ChatRoomSaveRequest;
import kr.co.fastcampus.yanabada.domain.chat.dto.response.ChatMessageInfoResponse;
import kr.co.fastcampus.yanabada.domain.chat.dto.response.ChatMessagePageResponse;
import kr.co.fastcampus.yanabada.domain.chat.dto.response.ChatRoomInfoResponse;
import kr.co.fastcampus.yanabada.domain.chat.dto.response.ChatRoomModifyResponse;
import kr.co.fastcampus.yanabada.domain.chat.dto.response.ChatRoomSummaryResponse;
import kr.co.fastcampus.yanabada.domain.chat.entity.ChatMessage;
import kr.co.fastcampus.yanabada.domain.chat.entity.ChatRoom;
import kr.co.fastcampus.yanabada.domain.chat.repository.ChatMessageRepository;
import kr.co.fastcampus.yanabada.domain.chat.repository.ChatRoomRepository;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.member.repository.MemberRepository;
import kr.co.fastcampus.yanabada.domain.notification.dto.ChatNotificationDto;
import kr.co.fastcampus.yanabada.domain.notification.service.NotificationService;
import kr.co.fastcampus.yanabada.domain.product.entity.Product;
import kr.co.fastcampus.yanabada.domain.product.entity.enums.ProductStatus;
import kr.co.fastcampus.yanabada.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;

    private final ChatMessageRepository chatMessageRepository;

    private final MemberRepository memberRepository;

    private final ProductRepository productRepository;

    private final NotificationService notificationService;

    @Transactional
    public ChatRoomInfoResponse getOrSaveChatRoom(ChatRoomSaveRequest request) {
        Product product = productRepository.getProduct(request.productId());
        checkNegotiationPossibility(product);
        Long sellerId = product.getOrder().getMember().getId();
        Long buyerId = request.buyerId();
        checkIfOwnProduct(sellerId, buyerId);
        return getChatRoomInfoResponse(request, product, sellerId, buyerId);
    }

    private void checkNegotiationPossibility(Product product) {
        if (!product.getCanNegotiate()
            || product.getStatus() != ProductStatus.ON_SALE
        ) {
            throw new NegotiationNotPossibleException();
        }
    }

    private void checkIfOwnProduct(Long sellerId, Long buyerId) {
        if (Objects.equals(sellerId, buyerId)) {
            throw new CannotNegotiateOwnProductException();
        }
    }

    private ChatRoomInfoResponse getChatRoomInfoResponse(
        ChatRoomSaveRequest request, Product product, Long sellerId, Long buyerId
    ) {
        Optional<ChatRoom> foundChatRoom = chatRoomRepository.findByProductIdAndSellerIdAndBuyerId(
            product.getId(), sellerId, buyerId
        );
        if (foundChatRoom.isPresent()) {
            return ChatRoomInfoResponse.from(foundChatRoom.get());
        } else {
            Member seller = memberRepository.getMember(sellerId);
            Member buyer = memberRepository.getMember(buyerId);
            LocalDateTime now = LocalDateTime.now();
            ChatRoom chatRoom = chatRoomRepository.save(
                request.toEntity(product, seller, buyer, now, now)
            );
            return ChatRoomInfoResponse.from(chatRoom);
        }
    }

    @Transactional(readOnly = true)
    public List<ChatRoomSummaryResponse> getChatRooms(Long memberId) {
        List<ChatRoom> chatRooms = chatRoomRepository.findChatRoomsByMemberId(memberId);
        List<ChatRoomSummaryResponse> chatRoomSummaryResponses = chatRooms.stream()
            .filter(chatRoom -> !chatRoom.getMessages().isEmpty())
            .map(chatRoom -> {
                List<ChatMessage> messages = chatRoom.getMessages();
                Member partner;
                int unreadCount;

                if (Objects.equals(memberId, chatRoom.getSeller().getId())) {
                    LocalDateTime sellerLastCheckTime = chatRoom.getSellerLastCheckTime();
                    partner = chatRoom.getBuyer();
                    unreadCount = calculateUnreadMessage(messages, sellerLastCheckTime);
                } else {
                    LocalDateTime buyerLastCheckTime = chatRoom.getBuyerLastCheckTime();
                    partner = chatRoom.getSeller();
                    unreadCount = calculateUnreadMessage(messages, buyerLastCheckTime);
                }

                return createChatRoomSummaryResponse(chatRoom, partner, messages, unreadCount);
            })
            .toList();
        return sortChatRoomSummaryResponse(chatRoomSummaryResponses);
    }

    private ChatRoomSummaryResponse createChatRoomSummaryResponse(
        ChatRoom chatRoom,
        Member partner,
        List<ChatMessage> messages,
        int unreadCount
    ) {
        return ChatRoomSummaryResponse.from(
            chatRoom.getCode(),
            partner,
            messages.get(messages.size() - 1),
            chatRoom.getProduct(),
            unreadCount
        );
    }

    private int calculateUnreadMessage(List<ChatMessage> messages, LocalDateTime lastCheckTime) {
        return (int) messages.stream()
            .takeWhile(message -> message.getSendDateTime().isAfter(lastCheckTime))
            .count();
    }

    private List<ChatRoomSummaryResponse> sortChatRoomSummaryResponse(
        List<ChatRoomSummaryResponse> responses
    ) {
        return responses.stream()
            .sorted((cr1, cr2) -> {
                LocalDateTime lastMessageTime1 = cr1.lastSentMessageTime();
                LocalDateTime lastMessageTime2 = cr2.lastSentMessageTime();
                return lastMessageTime2.compareTo(lastMessageTime1);
            })
            .toList();
    }

    @Transactional(readOnly = true)
    public ChatMessagePageResponse getChatRoomMessages(
        Long memberId, String chatRoomCode, Pageable pageable
    ) {
        ChatRoom chatRoom = chatRoomRepository.getChatroom(chatRoomCode);
        Member member = memberRepository.getMember(memberId);
        checkChatRoomMember(chatRoom, member);
        Page<ChatMessage> messages = chatMessageRepository.findByChatRoom(chatRoom, pageable);

        return ChatMessagePageResponse.from(
            chatRoomCode, convertChatMessagesToInfoResponses(messages)
        );
    }

    private void checkChatRoomMember(ChatRoom chatRoom, Member member) {
        if (!member.equals(chatRoom.getSeller()) && !member.equals(chatRoom.getBuyer())) {
            throw new IncorrectChatRoomMember();
        }
    }

    private Page<ChatMessageInfoResponse> convertChatMessagesToInfoResponses(
        Page<ChatMessage> messages
    ) {
        return messages.map(ChatMessageInfoResponse::from);
    }

    @Transactional
    public ChatRoomModifyResponse updateChatRoom(Long memberId, ChatRoomModifyRequest request) {
        ChatRoom chatRoom = chatRoomRepository.getChatroom(request.chatRoomCode());
        Member member = memberRepository.getMember(memberId);
        updateLastCheckTime(chatRoom, member);
        return ChatRoomModifyResponse.from(chatRoom.getCode(), memberId);
    }

    private void updateLastCheckTime(ChatRoom chatRoom, Member member) {
        checkChatRoomMember(chatRoom, member);
        LocalDateTime lastCheckTime = LocalDateTime.now();
        if (isSeller(member, chatRoom)) {
            chatRoom.updateSellerLastCheckTime(lastCheckTime);
        } else {
            chatRoom.updateBuyerLastCheckTime(lastCheckTime);
        }
    }

    @Transactional
    public ChatRoomModifyResponse modifyOrDeleteChatRoom(
        Long memberId, ChatRoomModifyRequest request
    ) {
        ChatRoom chatRoom = chatRoomRepository.getChatroom(request.chatRoomCode());
        Member member = memberRepository.getMember(memberId);
        checkChatRoomMember(chatRoom, member);
        LocalDateTime lastCheckTime = LocalDateTime.now();
        if (isSeller(member, chatRoom)) {
            handleSellerAction(chatRoom, lastCheckTime);
        } else {
            handleBuyerAction(chatRoom, lastCheckTime);
        }
        return ChatRoomModifyResponse.from(chatRoom.getCode(), memberId);
    }

    private boolean isSeller(Member member, ChatRoom chatRoom) {
        return member.equals(chatRoom.getSeller());
    }

    private boolean isBuyer(Member member, ChatRoom chatRoom) {
        return member.equals(chatRoom.getBuyer());
    }

    private void handleSellerAction(ChatRoom chatRoom, LocalDateTime lastCheckTime) {
        chatRoom.updateSellerLastCheckTime(lastCheckTime);
        if (chatRoom.getHasBuyerLeft()) {
            chatRoomRepository.delete(chatRoom);
        } else {
            chatRoom.updateHasSellerLeft(true);
        }
    }

    private void handleBuyerAction(ChatRoom chatRoom, LocalDateTime lastCheckTime) {
        chatRoom.updateBuyerLastCheckTime(lastCheckTime);
        if (chatRoom.getHasSellerLeft()) {
            chatRoomRepository.delete(chatRoom);
        } else {
            chatRoom.updateHasBuyerLeft(true);
        }
    }

    @Transactional
    public SendChatMessage saveChatMessage(ReceivedChatMessage message) {
        ChatRoom chatRoom = chatRoomRepository.getChatroom(message.chatRoomCode());
        Member sender = memberRepository.getMember(message.senderId());
        LocalDateTime sendTime = LocalDateTime.now();
        checkChatRoomMember(chatRoom, sender);
        updateMemberPresenceStatus(chatRoom, sender);
        if (chatRoom.getMessages().size() == 0) {
            sendFcmAndHistoryNotification(chatRoom, sender, message.content());
        } else {
            sendFcmNotification(chatRoom, sender, message.content());
        }
        addMessageToChatRoom(chatRoom, message, sender, sendTime);
        return createSendChatMessage(chatRoom, sender, message, sendTime);
    }

    private void addMessageToChatRoom(
        ChatRoom chatRoom, ReceivedChatMessage message, Member sender, LocalDateTime sendTime
    ) {
        chatRoom.addChatMessage(message.toEntity(chatRoom, sender, sendTime));
    }

    private void updateMemberPresenceStatus(ChatRoom chatRoom, Member sender) {
        if (isSeller(sender, chatRoom) && chatRoom.getHasBuyerLeft()) {
            chatRoom.updateHasBuyerLeft(false);
        } else if (isBuyer(sender, chatRoom) && chatRoom.getHasSellerLeft()) {
            chatRoom.updateHasSellerLeft(false);
        }
    }

    private SendChatMessage createSendChatMessage(
        ChatRoom chatRoom, Member sender, ReceivedChatMessage message, LocalDateTime sendTime
    ) {
        return SendChatMessage.from(chatRoom, sender, message.content(), sendTime);
    }

    private ChatNotificationDto createChatNotificationDto(
        Member sender, Member receiver, ChatRoom chatRoom
    ) {
        return ChatNotificationDto.from(sender, receiver, chatRoom);
    }

    private void sendFcmAndHistoryNotification(ChatRoom chatRoom, Member sender, String content) {
        notificationService.sendChatCreated(
            createChatNotificationDto(sender, chatRoom.getSeller(), chatRoom),
            content
        );
    }

    private void sendFcmNotification(ChatRoom chatRoom, Member sender, String content) {
        if (isSeller(sender, chatRoom)) {
            notificationService.sendChatMessage(sender, chatRoom.getBuyer(), content);
        } else {
            notificationService.sendChatMessage(sender, chatRoom.getSeller(), content);
        }
    }
}
