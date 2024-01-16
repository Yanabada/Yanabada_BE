package kr.co.fastcampus.yanabada.domain.payment.dto.response;

import java.util.List;

public record YanoljaPayHomeResponse(
    Long balance,
    List<PaymentHistoryResponse> paymentHistories
) {

}
