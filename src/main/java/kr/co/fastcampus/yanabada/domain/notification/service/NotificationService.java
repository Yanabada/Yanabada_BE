package kr.co.fastcampus.yanabada.domain.notification.service;

import static kr.co.fastcampus.yanabada.domain.notification.entity.enums.NotificationType.CHAT;
import static kr.co.fastcampus.yanabada.domain.notification.entity.enums.NotificationType.TRADE_APPROVAL;
import static kr.co.fastcampus.yanabada.domain.notification.entity.enums.NotificationType.TRADE_CANCELED;
import static kr.co.fastcampus.yanabada.domain.notification.entity.enums.NotificationType.TRADE_REJECTED;
import static kr.co.fastcampus.yanabada.domain.notification.entity.enums.NotificationType.TRADE_REQUEST;
import static kr.co.fastcampus.yanabada.domain.notification.property.NotificationProperties.CHAT_MESSAGE_TITLE;
import static kr.co.fastcampus.yanabada.domain.notification.property.NotificationProperties.EXPIRATION_DURATION;
import static kr.co.fastcampus.yanabada.domain.notification.property.NotificationProperties.TRADE_APPROVAL_CONTENT_POSTFIX;
import static kr.co.fastcampus.yanabada.domain.notification.property.NotificationProperties.TRADE_APPROVAL_TITLE;
import static kr.co.fastcampus.yanabada.domain.notification.property.NotificationProperties.TRADE_CANCELED_CONTENT_POSTFIX;
import static kr.co.fastcampus.yanabada.domain.notification.property.NotificationProperties.TRADE_CANCELED_CONTENT_PREFIX;
import static kr.co.fastcampus.yanabada.domain.notification.property.NotificationProperties.TRADE_CANCELED_TITLE;
import static kr.co.fastcampus.yanabada.domain.notification.property.NotificationProperties.TRADE_REJECTED_CONTENT_POSTFIX;
import static kr.co.fastcampus.yanabada.domain.notification.property.NotificationProperties.TRADE_REJECTED_TITLE;
import static kr.co.fastcampus.yanabada.domain.notification.property.NotificationProperties.TRADE_REQUEST_CONTENT_POSTFIX;
import static kr.co.fastcampus.yanabada.domain.notification.property.NotificationProperties.TRADE_REQUEST_TITLE;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Objects;
import kr.co.fastcampus.yanabada.common.exception.AccessForbiddenException;
import kr.co.fastcampus.yanabada.common.exception.JsonProcessFailedException;
import kr.co.fastcampus.yanabada.common.firebase.dto.request.FcmMessageRequest.Data;
import kr.co.fastcampus.yanabada.common.firebase.dto.request.FcmMessageRequest.Notification;
import kr.co.fastcampus.yanabada.common.firebase.service.FcmService;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.member.repository.MemberRepository;
import kr.co.fastcampus.yanabada.domain.notification.dto.ChatNotificationDto;
import kr.co.fastcampus.yanabada.domain.notification.dto.TradeNotificationDto;
import kr.co.fastcampus.yanabada.domain.notification.dto.request.NotificationDeleteRequest;
import kr.co.fastcampus.yanabada.domain.notification.dto.response.NotificationInfoResponse;
import kr.co.fastcampus.yanabada.domain.notification.dto.response.NotificationPageResponse;
import kr.co.fastcampus.yanabada.domain.notification.entity.NotificationHistory;
import kr.co.fastcampus.yanabada.domain.notification.repository.NotificationHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private static final String PNG_EXTENSION = ".png";
    private static final String CHAT_SENDER_NICKNAME_KEY = "senderNickname";
    private static final String ACCOMMODATION_NAME_KEY = "accommodationName";
    private static final String CRON_SCHEDULING = "0 0 0 * * *";

    @Value("${s3.end-point}")
    private String s3EndPoint;

    private final FcmService fcmService;
    private final NotificationHistoryRepository notificationHistoryRepository;
    private final ObjectMapper objectMapper;
    private final MemberRepository memberRepository;

    @Transactional
    public void sendChatMessage(Member sender, Member receiver, String content) {
        if (!receiver.getFcmToken().isEmpty()) {
            Notification notification = Notification.builder()
                .title(CHAT_MESSAGE_TITLE)
                .body(sender.getNickName() + ": " + content)
                .build();
            Data data = Data.builder().notificationType(CHAT.name()).build();
            fcmService.sendToMessage(receiver.getFcmToken(), notification, data);
        }
    }

    @Transactional
    public void sendChatCreated(ChatNotificationDto chatDto, String content) {
        if (!chatDto.receiver().getFcmToken().isEmpty()) {
            Notification notification = Notification.builder()
                .title(CHAT_MESSAGE_TITLE)
                .body(chatDto.sender().getNickName() + ": " + content)
                .build();
            Data data = Data.builder().notificationType(CHAT.name()).build();
            fcmService.sendToMessage(chatDto.receiver().getFcmToken(), notification, data);
        }

        NotificationHistory notificationHistory
            = NotificationHistory.builder()
            .receiver(chatDto.receiver())
            .notificationType(CHAT)
            .content(chatDto.convertMapToJsonStr(objectMapper))
            .image(chatDto.sender().getImage())
            .build();

        notificationHistoryRepository.save(notificationHistory);

    }

    @Transactional
    public void sendTradeRequest(TradeNotificationDto tradeNotificationDto) {
        if (!tradeNotificationDto.receiver().getFcmToken().isEmpty()) {
            Notification notification = Notification.builder()
                .title(TRADE_REQUEST_TITLE)
                .body(
                    getShortPhrase(tradeNotificationDto.accommodationName())
                        + TRADE_REQUEST_CONTENT_POSTFIX
                )
                .build();

            Data data = Data.builder().notificationType(TRADE_REQUEST.name()).build();

            fcmService.sendToMessage(
                tradeNotificationDto.receiver().getFcmToken(), notification, data
            );
        }

        NotificationHistory notificationHistory
            = NotificationHistory.builder()
            .receiver(tradeNotificationDto.receiver())
            .notificationType(TRADE_REQUEST)
            .content(tradeNotificationDto.convertMapToJsonStr(objectMapper))
            .image(s3EndPoint + TRADE_REQUEST.name().toLowerCase() + PNG_EXTENSION)
            .build();
        notificationHistoryRepository.save(notificationHistory);

    }

    @Transactional
    public void sendTradeCanceled(TradeNotificationDto tradeNotificationDto) {
        if (!tradeNotificationDto.receiver().getFcmToken().isEmpty()) {
            Notification notification = Notification.builder()
                .title(TRADE_CANCELED_TITLE)
                .body(
                    TRADE_CANCELED_CONTENT_PREFIX
                        + getShortPhrase(tradeNotificationDto.accommodationName())
                        + TRADE_CANCELED_CONTENT_POSTFIX
                )
                .build();

            Data data = Data.builder().notificationType(TRADE_CANCELED.name()).build();

            fcmService.sendToMessage(
                tradeNotificationDto.receiver().getFcmToken(), notification, data
            );
        }

        NotificationHistory notificationHistory
            = NotificationHistory.builder()
            .receiver(tradeNotificationDto.receiver())
            .notificationType(TRADE_CANCELED)
            .content(tradeNotificationDto.convertMapToJsonStr(objectMapper))
            .image(s3EndPoint + TRADE_CANCELED.name().toLowerCase() + PNG_EXTENSION)
            .build();
        notificationHistoryRepository.save(notificationHistory);

    }

    @Transactional
    public void sendTradeApproval(TradeNotificationDto tradeNotificationDto) {
        if (!tradeNotificationDto.receiver().getFcmToken().isEmpty()) {
            Notification notification = Notification.builder()
                .title(TRADE_APPROVAL_TITLE)
                .body(
                    getShortPhrase(tradeNotificationDto.accommodationName())
                        + TRADE_APPROVAL_CONTENT_POSTFIX
                )
                .build();

            Data data = Data.builder().notificationType(TRADE_APPROVAL.name()).build();

            fcmService.sendToMessage(
                tradeNotificationDto.receiver().getFcmToken(), notification, data
            );
        }

        NotificationHistory notificationHistory
            = NotificationHistory.builder()
            .receiver(tradeNotificationDto.receiver())
            .notificationType(TRADE_APPROVAL)
            .content(tradeNotificationDto.convertMapToJsonStr(objectMapper))
            .image(s3EndPoint + TRADE_APPROVAL.name().toLowerCase() + PNG_EXTENSION)
            .build();
        notificationHistoryRepository.save(notificationHistory);

    }

    @Transactional
    public void sendTradeRejected(TradeNotificationDto tradeNotificationDto) {
        if (!tradeNotificationDto.receiver().getFcmToken().isEmpty()) {
            Notification notification = Notification.builder()
                .title(TRADE_REJECTED_TITLE)
                .body(
                    getShortPhrase(tradeNotificationDto.accommodationName())
                        + TRADE_REJECTED_CONTENT_POSTFIX
                )
                .build();

            Data data = Data.builder().notificationType(TRADE_REJECTED.name()).build();

            fcmService.sendToMessage(
                tradeNotificationDto.receiver().getFcmToken(), notification, data
            );
        }

        NotificationHistory notificationHistory
            = NotificationHistory.builder()
            .receiver(tradeNotificationDto.receiver())
            .notificationType(TRADE_REJECTED)
            .content(tradeNotificationDto.convertMapToJsonStr(objectMapper))
            .image(s3EndPoint + TRADE_REJECTED.name().toLowerCase() + PNG_EXTENSION)
            .build();
        notificationHistoryRepository.save(notificationHistory);

    }

    private String getShortPhrase(String name) {
        if (name.length() <= 7) {
            return name;
        }
        return name.substring(0, 7) + "...";
    }

    @Transactional
    public NotificationPageResponse getNotifications(Long memberId, Pageable pageable) {
        Member member = memberRepository.getMember(memberId);
        Page<NotificationHistory> histories =
            notificationHistoryRepository.findByReceiver(member, pageable);
        Page<NotificationInfoResponse> responses = histories.map(history -> {
            NotificationInfoResponse response;
            if (history.getNotificationType().equals(CHAT)) {
                String content = history.getContent();
                String senderNickname = convertJsonToString(CHAT_SENDER_NICKNAME_KEY, content);
                String accommodationName = convertJsonToString(ACCOMMODATION_NAME_KEY, content);
                response = NotificationInfoResponse.from(
                    senderNickname, accommodationName, history
                );

            } else {
                String content = history.getContent();
                String accommodationName = convertJsonToString(ACCOMMODATION_NAME_KEY, content);
                response = NotificationInfoResponse.from(
                    null, accommodationName, history
                );
            }
            history.updateRead(true);
            return response;
        });

        return NotificationPageResponse.from(responses);

    }

    private String convertJsonToString(String key, String content) {
        try {
            JsonNode node = objectMapper.readTree(content);
            return node.get(key).asText();
        } catch (JsonProcessingException e) {
            throw new JsonProcessFailedException();
        }
    }

    @Transactional
    public void deleteNotifications(
        Long memberId, List<NotificationDeleteRequest> requests
    ) {
        Member member = memberRepository.getMember(memberId);
        requests.stream()
            .map(request -> notificationHistoryRepository.getNotificationHistory(request.id()))
            .forEach(notificationHistory -> {
                validateMemberAndNotificationHistory(member, notificationHistory);
                notificationHistoryRepository.delete(notificationHistory);
            });
    }

    private void validateMemberAndNotificationHistory(
        Member member, NotificationHistory notificationHistory
    ) {
        if (!Objects.equals(member.getId(), notificationHistory.getReceiver().getId())) {
            throw new AccessForbiddenException();
        }
    }

    @Scheduled(cron = CRON_SCHEDULING)
    @Transactional
    public void deleteExpiredNotificationHistories() {
        List<NotificationHistory> notificationHistories
            = notificationHistoryRepository.getByExpired(EXPIRATION_DURATION);
        notificationHistories.forEach(notificationHistoryRepository::delete);
    }

    @Transactional
    public void deleteAllNotifications(Long memberId) {
        Member member = memberRepository.getMember(memberId);
        notificationHistoryRepository.deleteAllByReceiver(member);
    }
}
