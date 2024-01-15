package kr.co.fastcampus.yanabada.domain.payment.repository;

import java.util.Optional;
import kr.co.fastcampus.yanabada.domain.payment.entity.YanoljaPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YanoljaPaymentRepository extends JpaRepository<YanoljaPayment, Long> {

    Optional<YanoljaPayment> findByMemberId(Long memberId);
}
