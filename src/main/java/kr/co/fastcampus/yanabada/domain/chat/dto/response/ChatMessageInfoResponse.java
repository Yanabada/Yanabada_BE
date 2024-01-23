package kr.co.fastcampus.yanabada.domain.chat.dto.response;

import java.time.LocalDateTime;
import kr.co.fastcampus.yanabada.domain.chat.entity.ChatMessage;
import lombok.Builder;

@Builder
public record ChatMessageInfoResponse(
    Long senderId,
    String senderImage,
    String senderNickname,
    String content,
    LocalDateTime sendDateTime
) {

    public static ChatMessageInfoResponse from(ChatMessage message) {
        return ChatMessageInfoResponse.builder()
            .senderId(message.getSender().getId())
            .senderImage(message.getSender().getImage())
            .senderNickname(message.getSender().getNickName())
            .content(message.getContent())
            .sendDateTime(message.getSendDateTime())
            .build();
    }
}
