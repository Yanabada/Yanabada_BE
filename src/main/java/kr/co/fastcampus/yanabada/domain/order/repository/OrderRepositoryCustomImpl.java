package kr.co.fastcampus.yanabada.domain.order.repository;

import static kr.co.fastcampus.yanabada.domain.order.entity.QOrder.order;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import kr.co.fastcampus.yanabada.domain.order.entity.Order;
import kr.co.fastcampus.yanabada.domain.order.entity.enums.OrderStatus;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Order> getByCheckInDateExpired() {
        return queryFactory.selectFrom(order)
            .where(
                equalStatus(OrderStatus.RESERVED),
                lessCheckInDate(LocalDate.now())
            )
            .fetch();
    }

    private BooleanExpression equalStatus(OrderStatus status) {
        if (status == null) {
            return null;
        }

        return order.status.eq(status);
    }

    private BooleanExpression lessCheckInDate(LocalDate date) {
        if (date == null) {
            return null;
        }

        return order.checkInDate.lt(date);
    }
}
