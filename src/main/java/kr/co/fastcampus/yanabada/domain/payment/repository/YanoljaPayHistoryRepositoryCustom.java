package kr.co.fastcampus.yanabada.domain.payment.repository;

import kr.co.fastcampus.yanabada.domain.payment.dto.request.YanoljaPayHistorySearchRequest;
import kr.co.fastcampus.yanabada.domain.payment.entity.YanoljaPay;
import kr.co.fastcampus.yanabada.domain.payment.entity.YanoljaPayHistory;
import org.springframework.data.domain.Page;

public interface YanoljaPayHistoryRepositoryCustom {

    Page<YanoljaPayHistory> getHistoriesByYanoljaPayAndSearchRequest(
        YanoljaPay yanoljaPay,
        YanoljaPayHistorySearchRequest request
    );
}
