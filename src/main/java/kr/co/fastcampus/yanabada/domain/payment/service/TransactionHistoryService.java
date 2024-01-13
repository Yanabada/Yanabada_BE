package kr.co.fastcampus.yanabada.domain.payment.service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.TransactionHistoryResponse;
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

    public List<TransactionHistoryResponse> getTransactionHistory(Long memberId) {
        List<YanabadaPaymentHistory> histories = yanabadaPaymentHistoryRepository.findByYanabadaPaymentId(memberId);
        return histories.stream()
            .map(history -> {
                String name = history.getYanabadaPayment().getBankName();
                String image = history.getYanabadaPayment().getBankImage();
                String accountNum = history.getYanabadaPayment().getAccountNumber();
                Long price = history.getChargePrice();
                LocalDateTime time = history.getTransactionTime();

                return new TransactionHistoryResponse(name, image, accountNum, price, time);
            })
            .collect(Collectors.toList());
    }
}

