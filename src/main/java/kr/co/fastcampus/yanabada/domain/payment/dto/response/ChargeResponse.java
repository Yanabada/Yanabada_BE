package kr.co.fastcampus.yanabada.domain.payment.dto.response;

import java.math.BigDecimal;

public record ChargeResponse(
    Long yanoljaPaymentId,
    BigDecimal newBalance,
    String message
) {

}
