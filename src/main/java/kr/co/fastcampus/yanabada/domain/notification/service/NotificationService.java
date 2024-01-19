package kr.co.fastcampus.yanabada.domain.notification.service;

import static kr.co.fastcampus.yanabada.domain.notification.entity.enums.NotificationType.CHAT;
import static kr.co.fastcampus.yanabada.domain.notification.entity.enums.NotificationType.TRADE_APPROVAL_CANCEL;
import static kr.co.fastcampus.yanabada.domain.notification.entity.enums.NotificationType.TRADE_APPROVAL_REJECTED;
import static kr.co.fastcampus.yanabada.domain.notification.entity.enums.NotificationType.TRADE_APPROVAL_REQUEST;
import static kr.co.fastcampus.yanabada.domain.notification.entity.enums.NotificationType.TRADE_APPROVAL_SUCCESS;
import static kr.co.fastcampus.yanabada.domain.notification.property.NotificationProperties.CHAT_CREATED_CONTENT_INFIX;
import static kr.co.fastcampus.yanabada.domain.notification.property.NotificationProperties.CHAT_CREATED_CONTENT_POSTFIX;
import static kr.co.fastcampus.yanabada.domain.notification.property.NotificationProperties.CHAT_CREATED_TITLE;
import static kr.co.fastcampus.yanabada.domain.notification.property.NotificationProperties.CHAT_MESSAGE_TITLE;
import static kr.co.fastcampus.yanabada.domain.notification.property.NotificationProperties.TRADE_APPROVAL_CANCELED_CONTENT_POSTFIX;
import static kr.co.fastcampus.yanabada.domain.notification.property.NotificationProperties.TRADE_APPROVAL_CANCELED_CONTENT_PREFIX;
import static kr.co.fastcampus.yanabada.domain.notification.property.NotificationProperties.TRADE_APPROVAL_CANCELED_TITLE;
import static kr.co.fastcampus.yanabada.domain.notification.property.NotificationProperties.TRADE_APPROVAL_REJECTED_CONTENT_POSTFIX;
import static kr.co.fastcampus.yanabada.domain.notification.property.NotificationProperties.TRADE_APPROVAL_REJECTED_TITLE;
import static kr.co.fastcampus.yanabada.domain.notification.property.NotificationProperties.TRADE_APPROVAL_REQUEST_CONTENT_POSTFIX;
import static kr.co.fastcampus.yanabada.domain.notification.property.NotificationProperties.TRADE_APPROVAL_REQUEST_TITLE;
import static kr.co.fastcampus.yanabada.domain.notification.property.NotificationProperties.TRADE_APPROVAL_SUCCESS_CONTENT_POSTFIX;
import static kr.co.fastcampus.yanabada.domain.notification.property.NotificationProperties.TRADE_APPROVAL_SUCCESS_TITLE;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.fastcampus.yanabada.common.firebase.dto.request.FcmMessageRequest.Data;
import kr.co.fastcampus.yanabada.common.firebase.dto.request.FcmMessageRequest.Notification;
import kr.co.fastcampus.yanabada.common.firebase.service.FcmService;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.notification.dto.ChatNotificationDto;
import kr.co.fastcampus.yanabada.domain.notification.dto.TradeNotificationDto;
import kr.co.fastcampus.yanabada.domain.notification.entity.NotificationHistory;
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
    private final ObjectMapper objectMapper;

    @Transactional
    public void sendChatMessage(Member sender, String content) {
        Notification notification = Notification.builder()
            .title(CHAT_MESSAGE_TITLE)
            .body(sender.getNickName() + ": " + content)
            .build();
        Data data = Data.builder().notificationType(CHAT.name()).build();
        fcmService.sendToMessage(sender.getFcmToken(), notification, data);
    }

    @Transactional
    public void sendChatCreated(ChatNotificationDto chatDto) {

        Notification notification = Notification.builder()
            .title(CHAT_CREATED_TITLE)
            .body(
                chatDto.sender().getNickName()
                    + CHAT_CREATED_CONTENT_INFIX
                    + getShortPhrase(chatDto.accommodationName())
                    + CHAT_CREATED_CONTENT_POSTFIX
            )
            .build();
        Data data = Data.builder().notificationType(CHAT.name()).build();
        fcmService.sendToMessage(chatDto.receiver().getFcmToken(), notification, data);

        NotificationHistory notificationHistory
            = NotificationHistory.builder()
            .receiver(chatDto.receiver())
            .notificationType(CHAT)
            .content(chatDto.convertMapToJsonStr(objectMapper))
            .image(chatDto.image())
            .build();

        notificationHistoryRepository.save(notificationHistory);

    }

    @Transactional
    public void sendTradeApprovalRequest(TradeNotificationDto tradeApprovalDto) {
        Notification notification = Notification.builder()
            .title(TRADE_APPROVAL_REQUEST_TITLE)
            .body(
                getShortPhrase(tradeApprovalDto.accommodationName())
                    + TRADE_APPROVAL_REQUEST_CONTENT_POSTFIX
            )
            .build();

        Data data = Data.builder().notificationType(TRADE_APPROVAL_REQUEST.name()).build();

        fcmService.sendToMessage(tradeApprovalDto.receiver().getFcmToken(), notification, data);

        NotificationHistory notificationHistory
            = NotificationHistory.builder()
            .receiver(tradeApprovalDto.receiver())
            .notificationType(TRADE_APPROVAL_REQUEST)
            .content(tradeApprovalDto.convertMapToJsonStr(objectMapper))
            .image(TRADE_APPROVAL_REQUEST.name().toLowerCase() + ".png")
            .build();
        notificationHistoryRepository.save(notificationHistory);

    }

    @Transactional
    public void sendTradeApprovalCancel(TradeNotificationDto tradeApprovalDto) {
        Notification notification = Notification.builder()
            .title(TRADE_APPROVAL_CANCELED_TITLE)
            .body(
                TRADE_APPROVAL_CANCELED_CONTENT_PREFIX
                    + getShortPhrase(tradeApprovalDto.accommodationName())
                    + TRADE_APPROVAL_CANCELED_CONTENT_POSTFIX
            )
            .build();

        Data data = Data.builder().notificationType(TRADE_APPROVAL_CANCEL.name()).build();

        fcmService.sendToMessage(tradeApprovalDto.receiver().getFcmToken(), notification, data);

        NotificationHistory notificationHistory
            = NotificationHistory.builder()
            .receiver(tradeApprovalDto.receiver())
            .notificationType(TRADE_APPROVAL_CANCEL)
            .content(tradeApprovalDto.convertMapToJsonStr(objectMapper))
            .image(TRADE_APPROVAL_CANCEL.name().toLowerCase() + ".png") //todo: png 상수처리?
            .build();
        notificationHistoryRepository.save(notificationHistory);

    }

    @Transactional
    public void sendTradeApprovalSuccess(TradeNotificationDto tradeApprovalDto) {
        Notification notification = Notification.builder()
            .title(TRADE_APPROVAL_SUCCESS_TITLE)
            .body(
                getShortPhrase(tradeApprovalDto.accommodationName())
                    + TRADE_APPROVAL_SUCCESS_CONTENT_POSTFIX
            )
            .build();

        Data data = Data.builder().notificationType(TRADE_APPROVAL_SUCCESS.name()).build();

        fcmService.sendToMessage(tradeApprovalDto.receiver().getFcmToken(), notification, data);

        NotificationHistory notificationHistory
            = NotificationHistory.builder()
            .receiver(tradeApprovalDto.receiver())
            .notificationType(TRADE_APPROVAL_SUCCESS)
            .content(tradeApprovalDto.convertMapToJsonStr(objectMapper))
            .image(TRADE_APPROVAL_SUCCESS.name().toLowerCase() + ".png")
            .build();
        notificationHistoryRepository.save(notificationHistory);

    }

    @Transactional
    public void sendTradeApprovalReject(TradeNotificationDto tradeApprovalDto) {
        Notification notification = Notification.builder()
            .title(TRADE_APPROVAL_REJECTED_TITLE)
            .body(
                getShortPhrase(tradeApprovalDto.accommodationName())
                    + TRADE_APPROVAL_REJECTED_CONTENT_POSTFIX
            )
            .build();

        Data data = Data.builder().notificationType(TRADE_APPROVAL_REJECTED.name()).build();

        fcmService.sendToMessage(tradeApprovalDto.receiver().getFcmToken(), notification, data);

        NotificationHistory notificationHistory
            = NotificationHistory.builder()
            .receiver(tradeApprovalDto.receiver())
            .notificationType(TRADE_APPROVAL_REJECTED)
            .content(tradeApprovalDto.convertMapToJsonStr(objectMapper))
            .image(TRADE_APPROVAL_REJECTED.name().toLowerCase() + ".png")
            .build();
        notificationHistoryRepository.save(notificationHistory);

    }

    private String getShortPhrase(String name) {
        return "'" + name.substring(0, 8) + (name.length() > 8 ? "..'" : "'");
    }
}
