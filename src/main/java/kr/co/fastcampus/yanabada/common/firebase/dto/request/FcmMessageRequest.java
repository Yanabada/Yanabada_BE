package kr.co.fastcampus.yanabada.common.firebase.dto.request;

import kr.co.fastcampus.yanabada.domain.notification.dto.request.ChatNotificationSendRequest;
import lombok.Builder;

@Builder
public record FcmMessageRequest(
    Boolean validateOnly,
    Message message

) {
    @Builder
    public record Message(
        Notification notification,
        Data data,
        String token
    ) {
    }

    @Builder
    public record Notification(
        String title,
        String body,
        String image
    ) {
    }

    @Builder
    public record Data(
        String senderName,
        String senderId,
        String message
    ) {
    }

}

