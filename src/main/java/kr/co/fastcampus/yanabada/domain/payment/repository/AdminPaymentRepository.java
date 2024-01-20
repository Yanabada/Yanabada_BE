package kr.co.fastcampus.yanabada.domain.payment.repository;

import kr.co.fastcampus.yanabada.common.exception.AdminPaymentNotFoundException;
import kr.co.fastcampus.yanabada.domain.payment.entity.AdminPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminPaymentRepository extends JpaRepository<AdminPayment, Long> {

    long ADMIN_PAYMENT_ID = 1L;

    default AdminPayment getAdminPayment() {
        return findById(ADMIN_PAYMENT_ID).orElseThrow(AdminPaymentNotFoundException::new);
    }
}
