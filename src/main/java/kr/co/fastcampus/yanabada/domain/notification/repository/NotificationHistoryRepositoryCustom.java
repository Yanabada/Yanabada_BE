package kr.co.fastcampus.yanabada.domain.notification.repository;

import java.util.List;
import kr.co.fastcampus.yanabada.domain.notification.entity.NotificationHistory;

public interface NotificationHistoryRepositoryCustom {

    List<NotificationHistory> getByExpired(long expirationDuration);
}
