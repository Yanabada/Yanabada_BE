package kr.co.fastcampus.yanabada.domain.chat.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import kr.co.fastcampus.yanabada.domain.chat.entity.ChatMessage;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import lombok.Builder;

@Builder
public record ChatRoomSummaryResponse(
    String chatRoomCode,
    String partnerImage,
    String partnerNickname,
    String lastChatMessage,
    @JsonFormat(pattern = "HH:mm")
    LocalTime lastSentMessageTime,
    String productName,
    Integer unreadMessageCount
) {

    public static ChatRoomSummaryResponse create(
        String chatRoomCode,
        Member partner,
        ChatMessage message,
        String productName,
        int unreadMessageCount
    ) {
        return ChatRoomSummaryResponse.builder()
            .chatRoomCode(chatRoomCode)
            .partnerImage(partner.getImageUrl())
            .partnerNickname(partner.getNickName())
            .lastChatMessage(message.getContent())
            .lastSentMessageTime(LocalTime.from(message.getSendDateTime()))
            .productName(productName)
            .unreadMessageCount(unreadMessageCount)
            .build();
    }
}
