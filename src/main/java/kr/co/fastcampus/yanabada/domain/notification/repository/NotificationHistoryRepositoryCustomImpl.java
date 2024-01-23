package kr.co.fastcampus.yanabada.domain.notification.repository;

import static kr.co.fastcampus.yanabada.domain.notification.entity.QNotificationHistory.notificationHistory;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import kr.co.fastcampus.yanabada.domain.notification.entity.NotificationHistory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotificationHistoryRepositoryCustomImpl
    implements NotificationHistoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<NotificationHistory> getByExpired(long expirationDuration) {
        return queryFactory
            .selectFrom(notificationHistory)
            .where(
                isExpired(expirationDuration)
            )
            .fetch();
    }

    private BooleanExpression isExpired(long expirationDuration) {
        return notificationHistory.registeredDate.before(
            LocalDate.now().minusDays(expirationDuration).atStartOfDay()
        );
    }
}
