package kr.co.fastcampus.yanabada.domain.payment.repository;

import java.util.List;
import kr.co.fastcampus.yanabada.domain.payment.entity.YanoljaPayHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YanoljaPayHistoryRepository extends
    JpaRepository<YanoljaPayHistory, Long> {
    List<YanoljaPayHistory> findByYanoljaPayId(Long yanoljaPayId);
}
