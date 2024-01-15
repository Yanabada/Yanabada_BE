package kr.co.fastcampus.yanabada.domain.payment.dto.request;

public record AccountLinkingRequest(
    String accountNumber, String bankName
) {

}
