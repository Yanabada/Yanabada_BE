package kr.co.fastcampus.yanabada.domain.payment.dto.response;

import java.math.BigDecimal;

public record ChargeResponse(
    Long YanoljaPaymentId,
    BigDecimal newBalance,
    String message
) {

}
