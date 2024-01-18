package kr.co.fastcampus.yanabada.domain.notification.repository;

import kr.co.fastcampus.yanabada.domain.notification.entity.NotificationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationHistoryRepository extends JpaRepository<NotificationHistory, Long> {
}
