package kr.co.fastcampus.yanabada.domain.notification.service;

import kr.co.fastcampus.yanabada.common.firebase.dto.request.FcmMessageRequest;
import kr.co.fastcampus.yanabada.common.firebase.service.FcmService;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.member.repository.MemberRepository;
import kr.co.fastcampus.yanabada.domain.notification.dto.request.ChatNotificationSendRequest;
import kr.co.fastcampus.yanabada.domain.notification.repository.NotificationBoxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final FcmService fcmService;
    private final NotificationBoxRepository notificationBoxRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void sendChatNotification(
        Long senderId,
        ChatNotificationSendRequest chatNotificationReq
    ) {
        Member sender = memberRepository.getMember(senderId);
        Member receiver = memberRepository.getMember(chatNotificationReq.receiverId());

        FcmMessageRequest.Notification fcmNotification
            = FcmMessageRequest.Notification.builder()
            .title("채팅 알림")
            .body(sender.getNickName() + " : " + chatNotificationReq.content())
            .build();

        fcmService.sendToMessage(sender, receiver, fcmNotification);
    }
}
