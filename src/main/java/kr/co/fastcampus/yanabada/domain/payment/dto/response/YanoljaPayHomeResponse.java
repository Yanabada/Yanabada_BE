package kr.co.fastcampus.yanabada.domain.payment.dto.response;

import java.util.List;
import java.util.stream.Collectors;
import kr.co.fastcampus.yanabada.domain.payment.entity.YanoljaPay;
import lombok.Builder;

@Builder
public record YanoljaPayHomeResponse(
    Long balance,
    List<PaymentHistoryResponse> paymentHistories
) {

    public static YanoljaPayHomeResponse from(YanoljaPay yanoljaPay) {
        List<PaymentHistoryResponse> paymentHistoryResponses =
            yanoljaPay.getPaymentHistories().stream()
                .map(PaymentHistoryResponse::from)
                .collect(Collectors.toList());

        return YanoljaPayHomeResponse.builder()
            .balance(yanoljaPay.getBalance())
            .paymentHistories(paymentHistoryResponses)
            .build();
    }
}
