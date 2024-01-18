package kr.co.fastcampus.yanabada.domain.product.repository;

import static kr.co.fastcampus.yanabada.domain.accommodation.entity.QAccommodation.accommodation;
import static kr.co.fastcampus.yanabada.domain.accommodation.entity.QAccommodationOption.accommodationOption;
import static kr.co.fastcampus.yanabada.domain.accommodation.entity.QRoom.room;
import static kr.co.fastcampus.yanabada.domain.accommodation.entity.QRoomOption.roomOption;
import static kr.co.fastcampus.yanabada.domain.accommodation.entity.enums.Category.GUESTHOUSE;
import static kr.co.fastcampus.yanabada.domain.accommodation.entity.enums.Category.HOTEL_RESORT;
import static kr.co.fastcampus.yanabada.domain.accommodation.entity.enums.Category.MOTEL;
import static kr.co.fastcampus.yanabada.domain.accommodation.entity.enums.Category.PENSION;
import static kr.co.fastcampus.yanabada.domain.accommodation.entity.enums.Category.POOL_VILLA;
import static kr.co.fastcampus.yanabada.domain.order.entity.QOrder.order;
import static kr.co.fastcampus.yanabada.domain.product.dto.request.enums.ProductSearchOrderCondition.RECENT;
import static kr.co.fastcampus.yanabada.domain.product.entity.QProduct.product;
import static kr.co.fastcampus.yanabada.domain.product.entity.enums.ProductStatus.BOOKING;
import static kr.co.fastcampus.yanabada.domain.product.entity.enums.ProductStatus.ON_SALE;
import static kr.co.fastcampus.yanabada.domain.product.entity.enums.ProductStatus.SOLD_OUT;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kr.co.fastcampus.yanabada.domain.order.entity.Order;
import kr.co.fastcampus.yanabada.domain.product.dto.request.ProductSearchRequest;
import kr.co.fastcampus.yanabada.domain.product.dto.request.enums.ProductSearchCategory;
import kr.co.fastcampus.yanabada.domain.product.dto.request.enums.ProductSearchOption;
import kr.co.fastcampus.yanabada.domain.product.dto.request.enums.ProductSearchOrderCondition;
import kr.co.fastcampus.yanabada.domain.product.entity.Product;
import kr.co.fastcampus.yanabada.domain.product.entity.enums.ProductStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    private static final int DEFAULT_PAGE_OFFSET = 0;
    private static final int DEFAULT_PAGE_SIZE = 20;

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Product> getBySearchRequest(ProductSearchRequest request) {
        int offset = getOffset(request.page());
        int limit = getLimit(request.size());
        Pageable pageable = PageRequest.of(offset, limit);

        JPAQuery<Product> query = createQuery(request);
        List<Product> products = query
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
        int totalCount = getTotalCount(createQuery(request));

        return new PageImpl<>(products, pageable, totalCount);
    }

    @Override
    public boolean existOnSaleOrBookingByOrder(Order order) {
        return !queryFactory.selectFrom(product)
            .where(
                equalOrder(order),
                containStatuses(ON_SALE, BOOKING)
            )
            .fetch()
            .isEmpty();
    }

    @Override
    public List<Product> getBySaleEndDateExpired() {
        return queryFactory.selectFrom(product)
            .where(
                containStatuses(ON_SALE, BOOKING),
                lessSaleEndDate(LocalDate.now())
            )
            .fetch();
    }

    private JPAQuery<Product> createQuery(ProductSearchRequest request) {
        NumberTemplate<Integer> salesPercentage = Expressions.numberTemplate(
            Integer.class,
            "(({0} - {1}) * 100) / {0}",
            order.price,
            product.price
        );
        NumberExpression<Integer> statusValue = new CaseBuilder()
            .when(product.status.eq(ON_SALE)).then(0)
            .when(product.status.eq(BOOKING)).then(1)
            .when(product.status.eq(SOLD_OUT)).then(2)
            .otherwise(3);

        return queryFactory
            .selectFrom(product)
            .join(product.order, order).fetchJoin()
            .join(order.room, room).fetchJoin()
            .join(room.accommodation, accommodation).fetchJoin()
            .leftJoin(room.roomOption, roomOption).fetchJoin()
            .leftJoin(accommodation.accommodationOption, accommodationOption).fetchJoin()
            .where(
                isStatusOnSaleOrBookingOrSoldOut(),
                containKeyword(request.keyword()),
                equalSchedule(request.checkInDate(), request.checkOutDate()),
                greaterOrEqualCapacity(request.adult(), request.child()),
                isInMap(request.smallX(), request.smallY(), request.bigX(), request.bigY()),
                hideSoldOut(request.isHidingSoldOut()),
                equalCategory(request.category()),
                hasOptions(request.options())
            )
            .orderBy(
                createOrderSpecifiers(request.order(), salesPercentage, statusValue)
                    .toArray(OrderSpecifier[]::new)
            );
    }

    private BooleanExpression isStatusOnSaleOrBookingOrSoldOut() {
        return product.status.in(ON_SALE, BOOKING, SOLD_OUT);
    }

    private BooleanExpression containKeyword(String keyword) {
        if (keyword == null) {
            return null;
        }

        return accommodation.address.contains(keyword).or(
            accommodation.name.contains(keyword)).or(
            room.name.contains(keyword));
    }

    private BooleanExpression equalSchedule(LocalDate from, LocalDate to) {
        if (from == null || to == null) {
            return null;
        }

        return order.checkInDate.eq(from).and(
            order.checkOutDate.eq(to));
    }

    private BooleanExpression greaterOrEqualCapacity(Integer adultCount, Integer childCount) {
        if (adultCount == null || childCount == null) {
            return null;
        }

        int totalCount = adultCount + childCount;

        if (childCount > 0) {
            return room.maxHeadCount.loe(totalCount).and(
                roomOption.isNoKids.eq(false)
            );
        }
        return room.maxHeadCount.loe(totalCount);
    }

    private BooleanExpression isInMap(
        Double smallX,
        Double smallY,
        Double bigX,
        Double bigY
    ) {
        if (smallX == null || smallY == null || bigX == null || bigY == null) {
            return null;
        }

        return accommodation.latitude.between(smallX, bigX).and(
            accommodation.longitude.between(smallY, bigY));
    }

    private BooleanExpression hideSoldOut(Boolean isHidingSoldOut) {
        if (isHidingSoldOut == null || !isHidingSoldOut) {
            return null;
        }

        return product.status.ne(SOLD_OUT);
    }

    private BooleanExpression equalCategory(ProductSearchCategory category) {
        if (category == null) {
            return null;
        }

        return switch (category) {
            case HOTEL_RESORT -> accommodation.category.eq(HOTEL_RESORT);
            case MOTEL -> accommodation.category.eq(MOTEL);
            case PENSION -> accommodation.category.eq(PENSION);
            case GUESTHOUSE -> accommodation.category.eq(GUESTHOUSE);
            case POOL_VILLA -> accommodation.category.eq(POOL_VILLA);
        };
    }

    private BooleanBuilder hasOptions(List<ProductSearchOption> options) {
        if (options == null) {
            return null;
        }

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        options.forEach(
            option -> booleanBuilder.and(hasOption(option))
        );

        return booleanBuilder;
    }

    private BooleanExpression hasOption(ProductSearchOption option) {
        return switch (option) {
            case SAUNA -> accommodationOption.hasSauna.eq(true);
            case GYM -> accommodationOption.hasGym.eq(true);
            case PARKING -> roomOption.canPark.eq(true);
            case ROOF_TOP -> accommodationOption.hasRooftop.eq(true);
            case LOUNGE_BAR -> accommodationOption.hasLoungeBar.eq(true);
            case PARTY_ROOM -> roomOption.isPartyRoom.eq(true);
            case POOL -> accommodationOption.hasPool.eq(true);
        };
    }

    private List<OrderSpecifier<?>> createOrderSpecifiers(
        ProductSearchOrderCondition orderCondition,
        NumberTemplate<Integer> salesPercentage,
        NumberExpression<Integer> statusValue
    ) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

        if (orderCondition == null) {
            orderCondition = RECENT;
        }

        switch (orderCondition) {
            case END_DATE_ASC -> {
                orderSpecifiers.add(product.saleEndDate.asc());
                orderSpecifiers.add(statusValue.asc());
            }
            case SALES_PERCENTAGE_DESC -> orderSpecifiers.add(salesPercentage.desc());
            case PRICE_ASC -> orderSpecifiers.add(product.price.asc());
            case RATING_DESC -> orderSpecifiers.add(room.rating.desc());
            default -> orderSpecifiers.add(product.id.desc());
        }

        return orderSpecifiers;
    }

    private BooleanExpression equalOrder(Order order) {
        if (order == null) {
            return null;
        }

        return product.order.eq(order);
    }

    private int getOffset(Integer page) {
        if (page == null || page <= 0) {
            return DEFAULT_PAGE_OFFSET;
        }

        return page - 1;
    }

    private int getLimit(Integer size) {
        if (size == null || size <= 0) {
            return DEFAULT_PAGE_SIZE;
        }

        return size;
    }

    private int getTotalCount(JPAQuery<?> query) {
        return query.fetch().size();
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

    private BooleanExpression lessSaleEndDate(LocalDate date) {
        if (date == null) {
            return null;
        }

        return product.saleEndDate.lt(date);
    }
}
