package kr.co.fastcampus.yanabada.domain.payment.dto.request;

import lombok.Builder;

@Builder
public record AccountLinkingRequest(
    String accountNumber, String bankName
) {

}
