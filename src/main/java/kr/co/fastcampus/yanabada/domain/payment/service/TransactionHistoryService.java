package kr.co.fastcampus.yanabada.domain.payment.service;

import org.springframework.stereotype.Service;
import java.util.List;
import kr.co.fastcampus.yanabada.domain.payment.entity.YanabadaPaymentHistory;
import kr.co.fastcampus.yanabada.domain.payment.repository.YanabadaPaymentHistoryRepository;

@Service
public class TransactionHistoryService {
    private final YanabadaPaymentHistoryRepository yanabadaPaymentHistoryRepository;

    public TransactionHistoryService(YanabadaPaymentHistoryRepository yanabadaPaymentHistoryRepository) {
        this.yanabadaPaymentHistoryRepository = yanabadaPaymentHistoryRepository;
    }

    public List<YanabadaPaymentHistory> getTransactionHistory(Long memberId) {
        // 사용자의 거래 내역 조회
        return yanabadaPaymentHistoryRepository.findByMemberId(memberId);
    }
}

