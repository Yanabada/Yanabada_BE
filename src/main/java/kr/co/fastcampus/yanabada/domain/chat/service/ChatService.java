package kr.co.fastcampus.yanabada.domain.chat.service;

import java.time.LocalDateTime;
import kr.co.fastcampus.yanabada.domain.chat.dto.request.ChatRoomSaveRequest;
import kr.co.fastcampus.yanabada.domain.chat.dto.response.ChatRoomIdResponse;
import kr.co.fastcampus.yanabada.domain.chat.entity.ChatRoom;
import kr.co.fastcampus.yanabada.domain.chat.repository.ChatMessageRepository;
import kr.co.fastcampus.yanabada.domain.chat.repository.ChatRoomRepository;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.member.repository.MemberRepository;
import kr.co.fastcampus.yanabada.domain.product.entity.Product;
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
    public ChatRoomIdResponse saveChatRoom(ChatRoomSaveRequest request) {
        Product product = productRepository.getProduct(request.productId());
        Member seller = memberRepository.getMember(product.getOrder().getMember().getId());
        Member buyer = memberRepository.getMember(request.buyerId());
        LocalDateTime now = LocalDateTime.now();
        ChatRoom chatRoom =chatRoomRepository.save(
            request.toEntity(product, seller, buyer, now, now)
        );
        return ChatRoomIdResponse.from(chatRoom);
    }
}
