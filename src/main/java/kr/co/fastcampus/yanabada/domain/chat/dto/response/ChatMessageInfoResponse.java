package kr.co.fastcampus.yanabada.domain.chat.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import lombok.Builder;

@Builder
public record ChatMessageInfoResponse(
    String senderImage,
    String senderNickname,
    String content,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime sendDateTime
) {

    public static ChatMessageInfoResponse from(
        Member sender, String content, LocalDateTime sendDateTime
    ) {
        return ChatMessageInfoResponse.builder()
            .senderImage(sender.getImageUrl())
            .senderNickname(sender.getNickName())
            .content(content)
            .sendDateTime(sendDateTime)
            .build();
    }
}
