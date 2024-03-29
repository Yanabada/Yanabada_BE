package kr.co.fastcampus.yanabada.domain.order.repository;

import static kr.co.fastcampus.yanabada.domain.accommodation.entity.QAccommodation.accommodation;
import static kr.co.fastcampus.yanabada.domain.accommodation.entity.QRoom.room;
import static kr.co.fastcampus.yanabada.domain.accommodation.entity.enums.RoomCancelPolicy.YNBD_1;
import static kr.co.fastcampus.yanabada.domain.accommodation.entity.enums.RoomCancelPolicy.YNBD_2;
import static kr.co.fastcampus.yanabada.domain.order.entity.QOrder.order;
import static kr.co.fastcampus.yanabada.domain.order.entity.enums.OrderStatus.RESERVED;
import static kr.co.fastcampus.yanabada.domain.product.entity.QProduct.product;
import static kr.co.fastcampus.yanabada.domain.product.entity.enums.ProductStatus.BOOKING;
import static kr.co.fastcampus.yanabada.domain.product.entity.enums.ProductStatus.ON_SALE;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.ComparableExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.order.entity.Order;
import kr.co.fastcampus.yanabada.domain.order.entity.enums.OrderStatus;
import kr.co.fastcampus.yanabada.domain.product.entity.enums.ProductStatus;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {

    private static final long ZERO_COUNT = 0L;

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Order> getSellableByMember(Member member) {
        NumberExpression<Long> count = product.count().coalesce(ZERO_COUNT);

        return queryFactory
            .selectFrom(order)
            .leftJoin(product).on(product.order.eq(order), containStatuses(ON_SALE, BOOKING))
            .join(order.room, room).fetchJoin()
            .join(room.accommodation, accommodation).fetchJoin()
            .where(
                equalMember(member),
                equalStatus(RESERVED),
                hasRoomCancelFee()
            )
            .groupBy(order)
            .having(count.eq(ZERO_COUNT))
            .orderBy(order.id.desc())
            .fetch();
    }

    @Override
    public List<Order> getByCheckInDateExpired() {
        return queryFactory.selectFrom(order)
            .where(
                equalStatus(OrderStatus.RESERVED),
                lessCheckInDate(LocalDate.now())
            )
            .fetch();
    }

    private BooleanBuilder containStatuses(ProductStatus... statuses) {
        if (statuses == null) {
            return null;
        }

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        Arrays.stream(statuses)
            .forEach(
                status -> {
                    if (status != null) {
                        booleanBuilder.or(product.status.eq(status));
                    }
                }
            );

        return booleanBuilder;
    }

    private BooleanExpression equalMember(Member member) {
        if (member == null) {
            return null;
        }

        return order.member.eq(member);
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

    private BooleanExpression hasRoomCancelFee() {
        return new CaseBuilder()
            .when(room.cancelPolicy.eq(YNBD_1)).then(hasRoomCancelFeeInYnbd_1())
            .when(room.cancelPolicy.eq(YNBD_2)).then(hasRoomCancelFeeInYnbd_2())
            .otherwise(true)
            .eq(true);
    }

    private ComparableExpression<Boolean> hasRoomCancelFeeInYnbd_1() {
        return new CaseBuilder()
            .when(
                order.checkInDate.loe(LocalDate.now().plusDays(YNBD_1.getFeeOccurrenceDuration()))
            )
            .then(true)
            .otherwise(false);
    }

    private ComparableExpression<Boolean> hasRoomCancelFeeInYnbd_2() {
        return new CaseBuilder()
            .when(
                order.checkInDate.loe(LocalDate.now().plusDays(YNBD_2.getFeeOccurrenceDuration()))
            )
            .then(true)
            .otherwise(false);
    }
}
