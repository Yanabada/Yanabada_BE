package kr.co.fastcampus.yanabada.domain.payment.service;

import org.springframework.stereotype.Service;
import kr.co.fastcampus.yanabada.domain.payment.entity.YanabadaPayment;
import kr.co.fastcampus.yanabada.domain.payment.repository.YanabadaPaymentRepository;
import kr.co.fastcampus.yanabada.domain.payment.entity.YanabadaPaymentHistory;
import kr.co.fastcampus.yanabada.domain.payment.repository.YanabadaPaymentHistoryRepository;

@Service
public class ChargeService {
    private final YanabadaPaymentRepository yanabadaPaymentRepository;
    private final YanabadaPaymentHistoryRepository yanabadaPaymentHistoryRepository;

    public ChargeService(YanabadaPaymentRepository yanabadaPaymentRepository, YanabadaPaymentHistoryRepository yanabadaPaymentHistoryRepository) {
        this.yanabadaPaymentRepository = yanabadaPaymentRepository;
        this.yanabadaPaymentHistoryRepository = yanabadaPaymentHistoryRepository;
    }

    public void chargeAccount(Long memberId, Long amount) {
        // 실제 은행 계좌와의 연동이 필요할 경우 추가 로직 구현 예정

        YanabadaPayment payment = yanabadaPaymentRepository.findByMemberId(memberId)
            .orElseThrow(() -> new IllegalArgumentException("계좌를 찾을 수 없습니다."));
        payment.updateBalance(amount);  // 잔액 업데이트
        yanabadaPaymentRepository.save(payment);

        // 충전 내역 기록
        recordTransaction(memberId, amount);
    }

    private void recordTransaction(Long memberId, Long amount) {
        YanabadaPaymentHistory history = new YanabadaPaymentHistory();
        history.setYanabadaPaymentId(memberId);
        history.setChargePrice(amount);
        yanabadaPaymentHistoryRepository.save(history);
    }
}

