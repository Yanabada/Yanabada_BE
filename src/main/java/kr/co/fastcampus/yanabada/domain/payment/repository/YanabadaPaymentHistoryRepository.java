package kr.co.fastcampus.yanabada.domain.payment.repository;

import java.util.List;
import kr.co.fastcampus.yanabada.domain.payment.entity.YanabadaPaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YanabadaPaymentHistoryRepository extends
    JpaRepository<YanabadaPaymentRepository, Long> {
    List<YanabadaPaymentHistory> findByYanabadaPaymentId(Long yanabadaPaymentId);
}
