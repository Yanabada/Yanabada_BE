package kr.co.fastcampus.yanabada.domain.notification.dto.response;

import java.util.List;
import lombok.Builder;
import org.springframework.data.domain.Page;

@Builder
public record NotificationPageResponse(
    List<NotificationInfoResponse> notifications,
    int pageNum,
    int pageSize,
    int totalPages,
    boolean isFirst,
    boolean isLast
) {

    public static NotificationPageResponse from(Page<NotificationInfoResponse> responsePage) {
        return NotificationPageResponse.builder()
            .notifications(responsePage.getContent())
            .pageNum(responsePage.getNumber())
            .pageSize(responsePage.getSize())
            .totalPages(responsePage.getTotalPages())
            .isFirst(responsePage.isFirst())
            .isLast(responsePage.isLast())
            .build();
    }
}
