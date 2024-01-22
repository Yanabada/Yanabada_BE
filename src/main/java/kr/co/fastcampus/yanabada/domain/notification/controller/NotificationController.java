package kr.co.fastcampus.yanabada.domain.notification.controller;

import kr.co.fastcampus.yanabada.common.response.ResponseBody;
import kr.co.fastcampus.yanabada.common.security.PrincipalDetails;
import kr.co.fastcampus.yanabada.domain.notification.dto.request.NotificationDeleteRequest;
import kr.co.fastcampus.yanabada.domain.notification.dto.response.NotificationIdResponse;
import kr.co.fastcampus.yanabada.domain.notification.dto.response.NotificationPageResponse;
import kr.co.fastcampus.yanabada.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseBody<NotificationPageResponse> getNotifications(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PageableDefault(sort = "registeredDate", direction = Sort.Direction.DESC)
        Pageable pageable
    ) {
        return ResponseBody.ok(
            notificationService.getNotifications(principalDetails.id(), pageable)
        );
    }

    @DeleteMapping
    public ResponseBody<Void> deleteNotifications(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @RequestBody List<NotificationDeleteRequest> requests
    ) {
        notificationService.deleteNotifications(principalDetails.id(), requests);
        return ResponseBody.ok();
    }
}
