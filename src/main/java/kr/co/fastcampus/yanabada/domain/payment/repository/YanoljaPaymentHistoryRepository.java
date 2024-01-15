package kr.co.fastcampus.yanabada.domain.payment.repository;

import java.util.List;
import kr.co.fastcampus.yanabada.domain.payment.entity.YanoljaPaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YanoljaPaymentHistoryRepository extends
    JpaRepository<YanoljaPaymentHistory, Long> {
    List<YanoljaPaymentHistory> findByYanoljaPaymentId(Long yanabadaPaymentId);
}
