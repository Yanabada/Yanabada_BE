package kr.co.fastcampus.yanabada.domain.payment.repository;

import kr.co.fastcampus.yanabada.common.exception.YanoljaPayHistoryNotFoundException;
import kr.co.fastcampus.yanabada.domain.payment.entity.YanoljaPayHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YanoljaPayHistoryRepository
    extends JpaRepository<YanoljaPayHistory, Long>, YanoljaPayHistoryRepositoryCustom {

    default YanoljaPayHistory getYanoljaPayHistory(Long id) {
        return findById(id).orElseThrow(YanoljaPayHistoryNotFoundException::new);
    }
}
