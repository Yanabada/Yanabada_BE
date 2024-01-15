package kr.co.fastcampus.yanabada.domain.payment.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.TransactionHistoryResponse;
import kr.co.fastcampus.yanabada.domain.payment.entity.YanoljaPayHistory;
import kr.co.fastcampus.yanabada.domain.payment.repository.YanoljaPayHistoryRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionHistoryService {
    private final YanoljaPayHistoryRepository yanoljaPayHistoryRepository;

    public TransactionHistoryService(
        YanoljaPayHistoryRepository yanoljaPayHistoryRepository) {
        this.yanoljaPayHistoryRepository = yanoljaPayHistoryRepository;
    }

    public List<TransactionHistoryResponse> getTransactionHistory(Long memberId) {
        List<YanoljaPayHistory> histories =
            yanoljaPayHistoryRepository.findByYanoljaPayId(memberId);
        return histories.stream()
            .map(history -> {
                String name = history.getYanoljaPay().getBankName();
                String image = history.getYanoljaPay().getBankImage();
                String accountNum = history.getYanoljaPay().getAccountNumber();
                Long price = history.getChargePrice();
                LocalDateTime time = history.getTransactionTime();

                return new TransactionHistoryResponse(name, image, accountNum, price, time);
            })
            .collect(Collectors.toList());
    }
}

