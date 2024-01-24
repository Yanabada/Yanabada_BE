package kr.co.fastcampus.yanabada.domain.payment.repository;

import static kr.co.fastcampus.yanabada.domain.payment.entity.QYanoljaPayHistory.yanoljaPayHistory;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import kr.co.fastcampus.yanabada.domain.payment.dto.request.YanoljaPayHistorySearchRequest;
import kr.co.fastcampus.yanabada.domain.payment.entity.YanoljaPay;
import kr.co.fastcampus.yanabada.domain.payment.entity.YanoljaPayHistory;
import kr.co.fastcampus.yanabada.domain.payment.entity.enums.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class YanoljaPayHistoryRepositoryCustomImpl implements YanoljaPayHistoryRepositoryCustom {

    private static final int DEFAULT_PAGE_OFFSET = 0;
    private static final int DEFAULT_PAGE_SIZE = 20;

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<YanoljaPayHistory> getHistoriesByYanoljaPayAndSearchRequest(
        YanoljaPay yanoljaPay,
        YanoljaPayHistorySearchRequest request
    ) {
        int offset = getOffset(request.page());
        int limit = getLimit(request.size());
        Pageable pageable = PageRequest.of(offset, limit);

        JPAQuery<YanoljaPayHistory> query = createQuery(yanoljaPay, request);
        List<YanoljaPayHistory> histories = query
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
        int totalCount = getTotalCount(createQuery(yanoljaPay, request));

        return new PageImpl<>(histories, pageable, totalCount);
    }

    private JPAQuery<YanoljaPayHistory> createQuery(
        YanoljaPay yanoljaPay,
        YanoljaPayHistorySearchRequest request
    ) {
        return queryFactory.selectFrom(yanoljaPayHistory)
            .where(
                equalYanoljaPay(yanoljaPay),
                isInTypes(request.types())
            )
            .orderBy(yanoljaPayHistory.id.desc());
    }

    private BooleanExpression equalYanoljaPay(YanoljaPay yanoljaPay) {
        if (yanoljaPay == null) {
            return null;
        }

        return yanoljaPayHistory.yanoljaPay.eq(yanoljaPay);
    }

    private BooleanExpression isInTypes(List<TransactionType> types) {
        if (types == null || types.isEmpty()) {
            return null;
        }

        return yanoljaPayHistory.transactionType.in(types);
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
}
