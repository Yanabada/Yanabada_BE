package kr.co.fastcampus.yanabada.domain.payment.service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.TransactionHistoryResponse;
import org.springframework.stereotype.Service;
import java.util.List;
import kr.co.fastcampus.yanabada.domain.payment.entity.YanoljaPaymentHistory;
import kr.co.fastcampus.yanabada.domain.payment.repository.YanoljaPaymentHistoryRepository;

@Service
public class TransactionHistoryService {
    private final YanoljaPaymentHistoryRepository yanoljaPaymentHistoryRepository;

    public TransactionHistoryService(
        YanoljaPaymentHistoryRepository yanoljaPaymentHistoryRepository) {
        this.yanoljaPaymentHistoryRepository = yanoljaPaymentHistoryRepository;
    }

    public List<TransactionHistoryResponse> getTransactionHistory(Long memberId) {
        List<YanoljaPaymentHistory> histories = yanoljaPaymentHistoryRepository.findByYanoljaPaymentId(memberId);
        return histories.stream()
            .map(history -> {
                String name = history.getYanoljaPayment().getBankName();
                String image = history.getYanoljaPayment().getBankImage();
                String accountNum = history.getYanoljaPayment().getAccountNumber();
                Long price = history.getChargePrice();
                LocalDateTime time = history.getTransactionTime();

                return new TransactionHistoryResponse(name, image, accountNum, price, time);
            })
            .collect(Collectors.toList());
    }
}

