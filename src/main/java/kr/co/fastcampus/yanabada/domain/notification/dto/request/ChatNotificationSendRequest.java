package kr.co.fastcampus.yanabada.domain.notification.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record ChatNotificationSendRequest(
    @NotEmpty(message = "수신자 ID가 비어있습니다")
    Long receiverId,
    @NotEmpty(message = "알림 내용이 비어있습니다")
    String content
) {
}
