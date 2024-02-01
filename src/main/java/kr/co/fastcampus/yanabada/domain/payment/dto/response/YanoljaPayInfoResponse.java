package kr.co.fastcampus.yanabada.domain.payment.dto.response;

import kr.co.fastcampus.yanabada.domain.payment.entity.YanoljaPay;
import lombok.Builder;

@Builder
public record YanoljaPayInfoResponse(
    Long balance,
    String bankName,
    String accountNumber
) {
    public static YanoljaPayInfoResponse from(YanoljaPay yanoljaPay) {
        return YanoljaPayInfoResponse.builder()
            .balance(yanoljaPay.getBalance())
            .bankName(yanoljaPay.getBankName())
            .accountNumber(yanoljaPay.getAccountNumber())
            .build();
    }
}
