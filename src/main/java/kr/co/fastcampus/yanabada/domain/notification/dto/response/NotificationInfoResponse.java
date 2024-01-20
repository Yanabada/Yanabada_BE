package kr.co.fastcampus.yanabada.domain.notification.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import kr.co.fastcampus.yanabada.domain.notification.entity.NotificationHistory;
import kr.co.fastcampus.yanabada.domain.notification.entity.enums.NotificationType;
import lombok.Builder;

@Builder
public record NotificationInfoResponse(
    Long notificationId,
    String senderNickname,
    String accommodationName,
    NotificationType type,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime registeredDate,
    String image,
    Boolean isRead
) {

    public static NotificationInfoResponse from(
        String senderNickname,
        String accommodationName,
        NotificationHistory history
    ) {
        return NotificationInfoResponse.builder()
            .notificationId(history.getId())
            .senderNickname(senderNickname)
            .accommodationName(accommodationName)
            .type(history.getNotificationType())
            .registeredDate(history.getRegisteredDate())
            .image(history.getImage())
            .isRead(history.getIsRead())
            .build();
    }
}
