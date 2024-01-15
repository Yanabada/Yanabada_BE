package kr.co.fastcampus.yanabada.domain.payment.service;

import java.time.LocalDateTime;
import kr.co.fastcampus.yanabada.domain.payment.entity.YanoljaPayment;
import kr.co.fastcampus.yanabada.domain.payment.entity.YanoljaPaymentHistory;
import org.springframework.stereotype.Service;
import kr.co.fastcampus.yanabada.domain.payment.repository.YanoljaPaymentRepository;
import kr.co.fastcampus.yanabada.domain.payment.repository.YanoljaPaymentHistoryRepository;

@Service
public class ChargeService {
    private final YanoljaPaymentRepository yanoljaPaymentRepository;
    private final YanoljaPaymentHistoryRepository yanoljaPaymentHistoryRepository;

    public ChargeService(YanoljaPaymentRepository yanoljaPaymentRepository, YanoljaPaymentHistoryRepository yanoljaPaymentHistoryRepository) {
        this.yanoljaPaymentRepository = yanoljaPaymentRepository;
        this.yanoljaPaymentHistoryRepository = yanoljaPaymentHistoryRepository;
    }

    public void chargeAccount(Long memberId, Long amount) {
        // 실제 은행 계좌와의 연동이 필요할 경우 추가 로직 구현 예정

        YanoljaPayment payment = yanoljaPaymentRepository.findByMemberId(memberId)
            .orElseThrow(() -> new IllegalArgumentException("계좌를 찾을 수 없습니다."));
        payment.updateBalance(amount);  // 잔액 업데이트
        yanoljaPaymentRepository.save(payment);

        // 충전 내역 기록
        recordTransaction(memberId, amount);
    }

    private void recordTransaction(Long memberId, Long amount) {
        YanoljaPaymentHistory history = new YanoljaPaymentHistory();
        YanoljaPayment payment = yanoljaPaymentRepository.findByMemberId(memberId)
            .orElseThrow(() -> new IllegalArgumentException("계좌를 찾을 수 없습니다."));

        history.setYanabadaPayment(payment);
        history.setChargePrice(amount);
        history.setTransactionTime(LocalDateTime.now()); // 현재 시간을 거래 시간으로 설정
        yanoljaPaymentHistoryRepository.save(history);
    }
}

