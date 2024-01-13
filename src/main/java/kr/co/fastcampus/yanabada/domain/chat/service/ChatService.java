package kr.co.fastcampus.yanabada.domain.chat.service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import kr.co.fastcampus.yanabada.common.exception.CannotNegotiateOwnProductException;
import kr.co.fastcampus.yanabada.common.exception.NegotiationNotPossibleException;
import kr.co.fastcampus.yanabada.domain.chat.dto.request.ChatRoomSaveRequest;
import kr.co.fastcampus.yanabada.domain.chat.dto.response.ChatRoomInfoResponse;
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
}
