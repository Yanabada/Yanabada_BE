package kr.co.fastcampus.yanabada.domain.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface YanabadaPaymentHistoryRepository extends
    JpaRepository<YanabadaPaymentRepository, Long> {

}
