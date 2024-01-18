package kr.co.fastcampus.yanabada.domain.notification.controller;

import kr.co.fastcampus.yanabada.common.response.ResponseBody;
import kr.co.fastcampus.yanabada.common.security.PrincipalDetails;
import kr.co.fastcampus.yanabada.domain.notification.dto.request.ChatNotificationSendRequest;
import kr.co.fastcampus.yanabada.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/chat")
    public ResponseBody<Void> sendChatNotification(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @RequestBody ChatNotificationSendRequest chatNotificationReq
    ) {
        notificationService.sendChatNotification(principalDetails.id(), chatNotificationReq);

        return ResponseBody.ok();
    }
}