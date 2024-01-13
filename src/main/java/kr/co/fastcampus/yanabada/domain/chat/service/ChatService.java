package kr.co.fastcampus.yanabada.domain.chat.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import kr.co.fastcampus.yanabada.common.exception.CannotNegotiateOwnProductException;
import kr.co.fastcampus.yanabada.common.exception.IncorrectChatRoomMember;
import kr.co.fastcampus.yanabada.common.exception.NegotiationNotPossibleException;
import kr.co.fastcampus.yanabada.domain.chat.dto.request.ChatRoomSaveRequest;
import kr.co.fastcampus.yanabada.domain.chat.dto.response.ChatMessageInfoResponse;
import kr.co.fastcampus.yanabada.domain.chat.dto.response.ChatRoomInfoResponse;
import kr.co.fastcampus.yanabada.domain.chat.dto.response.ChatRoomSummaryResponse;
import kr.co.fastcampus.yanabada.domain.chat.entity.ChatMessage;
import kr.co.fastcampus.yanabada.domain.chat.entity.ChatRoom;
import kr.co.fastcampus.yanabada.domain.chat.repository.ChatMessageRepository;
import kr.co.fastcampus.yanabada.domain.chat.repository.ChatRoomRepository;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.member.repository.MemberRepository;
import kr.co.fastcampus.yanabada.domain.product.entity.Product;
import kr.co.fastcampus.yanabada.domain.product.entity.enums.ProductStatus;
import kr.co.fastcampus.yanabada.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;

    private final ChatMessageRepository chatMessageRepository;

    private final MemberRepository memberRepository;

    private final ProductRepository productRepository;

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
        return ChatRoomSummaryResponse.create(
            chatRoom.getCode(),
            partner,
            messages.get(messages.size() - 1),
            chatRoom.getProduct(),
            unreadCount
        );
    }

    private int calculateUnreadMessage(List<ChatMessage> messages, LocalDateTime lastCheckTime) {
        int unreadCount = 0;
        for (int i = messages.size() - 1; i >= 0; i--) {
            ChatMessage message = messages.get(i);
            if (message.getSendDateTime().isAfter(lastCheckTime)) {
                unreadCount++;
            } else {
                break;
            }
        }
        return unreadCount;
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
    public List<ChatMessageInfoResponse> getChatRoomMessages(Long memberId, String chatRoomCode) {
        ChatRoom chatRoom = chatRoomRepository.getChatroom(chatRoomCode);
        Member member = memberRepository.getMember(memberId);
        checkChatRoomMember(chatRoom, member);
        return chatRoom.getMessages().stream()
            .map(message -> ChatMessageInfoResponse.create(
                message.getSender(), message.getContent(), message.getSendDateTime()
            ))
            .toList();
    }

    private void checkChatRoomMember(ChatRoom chatRoom ,Member member) {
        if (!member.equals(chatRoom.getSeller()) && !member.equals(chatRoom.getBuyer())) {
            throw new IncorrectChatRoomMember();
        }
    }
}
