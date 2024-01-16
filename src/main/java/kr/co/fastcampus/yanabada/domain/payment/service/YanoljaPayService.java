package kr.co.fastcampus.yanabada.domain.payment.service;

import java.util.List;
import java.util.stream.Collectors;
import kr.co.fastcampus.yanabada.common.exception.MemberNotFoundException;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.PaymentHistoryResponse;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.YanoljaPayHomeResponse;
import kr.co.fastcampus.yanabada.domain.payment.entity.YanoljaPay;
import kr.co.fastcampus.yanabada.domain.payment.repository.YanoljaPayRepository;
import org.springframework.stereotype.Service;

@Service
public class YanoljaPayService {

    private final YanoljaPayRepository yanoljaPayRepository;

    public YanoljaPayService(YanoljaPayRepository yanoljaPayRepository) {
        this.yanoljaPayRepository = yanoljaPayRepository;
    }

    public YanoljaPayHomeResponse getHomeScreenData(Long memberId) {
        YanoljaPay yanoljaPay = yanoljaPayRepository.findByMemberId(memberId)
            .orElseThrow(() -> new MemberNotFoundException());

        List<PaymentHistoryResponse> paymentHistoryResponses =
            yanoljaPay.getPaymentHistories().stream()
            .map(history -> {
                YanoljaPay payInfo = history.getYanoljaPay();
                return new PaymentHistoryResponse(
                    "상품명", // todo: 상품명 정보가 필요할 경우, 별도의 처리가 필요.
                    history.getTransactionType().getDescription(),
                    payInfo.getBankName(),
                    payInfo.getBankImage(),
                    payInfo.getAccountNumber(),
                    history.getTransactionAmount(),
                    history.getTransactionTime()
                );
            }).collect(Collectors.toList());

        return new YanoljaPayHomeResponse(yanoljaPay.getBalance(), paymentHistoryResponses);
    }
}