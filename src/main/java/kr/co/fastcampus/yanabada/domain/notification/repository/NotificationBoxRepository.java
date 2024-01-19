package kr.co.fastcampus.yanabada.domain.notification.repository;

import kr.co.fastcampus.yanabada.domain.notification.entity.NotificationBox;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationBoxRepository extends JpaRepository<NotificationBox, Long> {
}
