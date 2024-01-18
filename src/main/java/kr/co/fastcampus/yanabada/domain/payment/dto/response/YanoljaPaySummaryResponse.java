package kr.co.fastcampus.yanabada.domain.payment.dto.response;

import kr.co.fastcampus.yanabada.domain.payment.entity.YanoljaPay;
import lombok.Builder;

@Builder
public record YanoljaPaySummaryResponse(
    Long balance,
    Boolean hasJoinedYanoljaPay
) {

    public static YanoljaPaySummaryResponse from(YanoljaPay yanoljaPay) {
        return YanoljaPaySummaryResponse.builder()
            .balance(yanoljaPay.getBalance())
            .hasJoinedYanoljaPay(yanoljaPay.getAccountNumber() != null)
            .build();
    }
}
