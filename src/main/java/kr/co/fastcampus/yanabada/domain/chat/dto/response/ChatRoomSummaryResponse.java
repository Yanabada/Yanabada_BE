package kr.co.fastcampus.yanabada.domain.chat.dto.response;

import java.time.LocalDateTime;
import kr.co.fastcampus.yanabada.domain.chat.entity.ChatMessage;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.product.entity.Product;
import lombok.Builder;

@Builder
public record ChatRoomSummaryResponse(
    String chatRoomCode,
    String partnerImage,
    String partnerNickname,
    String lastChatMessage,
    LocalDateTime lastSentMessageTime,
    Long productId,
    String productName,
    Integer unreadMessageCount
) {

    public static ChatRoomSummaryResponse create(
        String chatRoomCode,
        Member partner,
        ChatMessage message,
        Product product,
        int unreadMessageCount
    ) {
        return ChatRoomSummaryResponse.builder()
            .chatRoomCode(chatRoomCode)
            .partnerImage(partner.getImageUrl())
            .partnerNickname(partner.getNickName())
            .lastChatMessage(message.getContent())
            .lastSentMessageTime(message.getSendDateTime())
            .productId(product.getId())
            .productName(product.getOrder().getRoom().getAccommodation().getName())
            .unreadMessageCount(unreadMessageCount)
            .build();
    }
}
