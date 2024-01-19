package kr.co.fastcampus.yanabada.domain.notification.service;

import static kr.co.fastcampus.yanabada.domain.notification.entity.enums.NotificationType.CHAT;
import static kr.co.fastcampus.yanabada.domain.notification.entity.enums.NotificationType.TRADE_APPROVAL;

import kr.co.fastcampus.yanabada.common.firebase.dto.request.FcmMessageRequest;
import kr.co.fastcampus.yanabada.common.firebase.dto.request.FcmMessageRequest.Data;
import kr.co.fastcampus.yanabada.common.firebase.dto.request.FcmMessageRequest.Notification;
import kr.co.fastcampus.yanabada.common.firebase.service.FcmService;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.member.repository.MemberRepository;
import kr.co.fastcampus.yanabada.domain.notification.dto.request.ChatNotifyRequest;
import kr.co.fastcampus.yanabada.domain.notification.dto.request.SaleApprovalNotifyRequest;
import kr.co.fastcampus.yanabada.domain.notification.entity.enums.NotificationType;
import kr.co.fastcampus.yanabada.domain.notification.repository.NotificationHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final FcmService fcmService;
    private final NotificationHistoryRepository notificationHistoryRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void sendChatNotification(
        Long senderId,
        ChatNotifyRequest chatNotificationReq
    ) {
        Member sender = memberRepository.getMember(senderId);
        Member receiver = memberRepository.getMember(chatNotificationReq.receiverId());

        Notification notification = Notification.builder()
            .title("채팅 알림")
            .body(sender.getNickName() + " : " + chatNotificationReq.content())
            .build();

        Data data = Data.builder().notificationType(CHAT.name()).build();

        fcmService.sendToMessage(sender, receiver, notification, data);
    }

    @Transactional
    public void sendSaleApprovalNotification(
        Long senderId,
        SaleApprovalNotifyRequest saleApprovalNotifyRequest
    ) {
        Member sender = memberRepository.getMember(senderId);
        Member receiver = memberRepository.getMember(saleApprovalNotifyRequest.receiverId());

        Notification notification = Notification.builder()
            .title("판매 승인 요청 알림")
            .body(sender.getNickName() + "님으로부터 판매 승인 요청이 있습니다.")
            .build();

        Data data = Data.builder().notificationType(TRADE_APPROVAL.name()).build();

        fcmService.sendToMessage(sender, receiver, notification, data);
    }
}
