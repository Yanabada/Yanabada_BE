package kr.co.fastcampus.yanabada.domain.payment.repository;

import java.util.Optional;
import kr.co.fastcampus.yanabada.domain.payment.entity.YanabadaPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YanabadaPaymentRepository extends JpaRepository<YanabadaPayment, Long> {

    Optional<YanabadaPayment> findByMemberId(Long memberId);
}
