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

    public YanoljaPayHomeResponse getYanoljaPayData(Long memberId) {
        YanoljaPay yanoljaPay = yanoljaPayRepository.findByMemberId(memberId)
            .orElseThrow(MemberNotFoundException::new);

        List<PaymentHistoryResponse> paymentHistoryResponses =
            yanoljaPay.getPaymentHistories().stream()
            .map(PaymentHistoryResponse::from)
                .collect(Collectors.toList());

        return new YanoljaPayHomeResponse(yanoljaPay.getBalance(), paymentHistoryResponses);
    }
}