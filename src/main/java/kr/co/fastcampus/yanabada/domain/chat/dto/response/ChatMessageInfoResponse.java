package kr.co.fastcampus.yanabada.domain.chat.dto.response;

import java.time.LocalDateTime;
import kr.co.fastcampus.yanabada.domain.chat.entity.ChatMessage;
import lombok.Builder;

@Builder
public record ChatMessageInfoResponse(
    Long senderId,
    String senderProfileImage,
    String senderNickname,
    String content,
    LocalDateTime sendTime
) {

    public static ChatMessageInfoResponse from(ChatMessage message) {
        return ChatMessageInfoResponse.builder()
            .senderId(message.getSender().getId())
            .senderProfileImage(message.getSender().getImage())
            .senderNickname(message.getSender().getNickName())
            .content(message.getContent())
            .sendTime(message.getSendDateTime())
            .build();
    }
}
