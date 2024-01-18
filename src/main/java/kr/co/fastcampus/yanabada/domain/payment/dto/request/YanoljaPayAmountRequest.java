package kr.co.fastcampus.yanabada.domain.payment.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record YanoljaPayAmountRequest(
    @NotNull
    @Positive
    Long amount,

    @NotEmpty
    String simplePassword
) {

}
