package kr.co.fastcampus.yanabada.domain.payment.dto.request;


public record ChargeRequest(
    Long yanabadaPaymentId, Long amount
) {

}
