package kr.co.fastcampus.yanabada.domain.notification.repository;

import kr.co.fastcampus.yanabada.common.exception.NotificationNotFoundException;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.notification.entity.NotificationHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationHistoryRepository
    extends JpaRepository<NotificationHistory, Long>, NotificationHistoryRepositoryCustom {

    Page<NotificationHistory> findByReceiver(Member receiver, Pageable pageable);

    default NotificationHistory getNotificationHistory(Long id) {
        return findById(id).orElseThrow(NotificationNotFoundException::new);
    }

    void deleteAllByReceiver(Member receiver);
}
