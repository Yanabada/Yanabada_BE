package kr.co.fastcampus.yanabada.domain.payment.service;

import java.time.LocalDateTime;
import kr.co.fastcampus.yanabada.domain.payment.entity.YanoljaPay;
import kr.co.fastcampus.yanabada.domain.payment.entity.YanoljaPayHistory;
import kr.co.fastcampus.yanabada.domain.payment.repository.YanoljaPayHistoryRepository;
import kr.co.fastcampus.yanabada.domain.payment.repository.YanoljaPayRepository;
import org.springframework.stereotype.Service;

@Service
public class ChargeService {
    private final YanoljaPayRepository yanoljaPayRepository;
    private final YanoljaPayHistoryRepository yanoljaPayHistoryRepository;

    public ChargeService(YanoljaPayRepository yanoljaPayRepository,
        YanoljaPayHistoryRepository yanoljaPayHistoryRepository) {
        this.yanoljaPayRepository = yanoljaPayRepository;
        this.yanoljaPayHistoryRepository = yanoljaPayHistoryRepository;
    }

    public void chargeAccount(Long memberId, Long amount) {
        // 실제 은행 계좌와의 연동이 필요할 경우 추가 로직 구현 예정

        YanoljaPay payment = yanoljaPayRepository.findByMemberId(memberId)
            .orElseThrow(() -> new IllegalArgumentException("계좌를 찾을 수 없습니다."));
        payment.updateBalance(amount);  // 잔액 업데이트
        yanoljaPayRepository.save(payment);

        // 충전 내역 기록
        YanoljaPayHistory history = YanoljaPayHistory.create(
            payment, amount, LocalDateTime.now()
        );
        yanoljaPayHistoryRepository.save(history);
    }

    private void recordTransaction(Long memberId, Long amount) {
        YanoljaPay payment = yanoljaPayRepository.findByMemberId(memberId)
            .orElseThrow(() -> new IllegalArgumentException("계좌를 찾을 수 없습니다."));

        YanoljaPayHistory history = YanoljaPayHistory.create(
            payment, amount, LocalDateTime.now()
        );
        yanoljaPayHistoryRepository.save(history);
    }
}

