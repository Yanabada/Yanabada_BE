package kr.co.fastcampus.yanabada.domain.notification.dto.response;

import kr.co.fastcampus.yanabada.domain.notification.entity.NotificationHistory;
import lombok.Builder;

@Builder
public record NotificationIdResponse(
    Long deletedNotificationId
) {

    public static NotificationIdResponse from(NotificationHistory notificationHistory) {
        return NotificationIdResponse.builder()
            .deletedNotificationId(notificationHistory.getId())
            .build();
    }
}
