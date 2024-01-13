package kr.co.fastcampus.yanabada.domain.payment.dto.request;

import java.math.BigDecimal;

public record ChargeRequest(
    Long yanabadaPaymentId, Long amount
) {

}
