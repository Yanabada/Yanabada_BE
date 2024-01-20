package kr.co.fastcampus.yanabada.domain.payment.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record YanoljaPayAmountRequest(
    @NotNull
    @Positive
    Long amount,

    @NotEmpty
    @Size(min = 6, max = 6)
    @Pattern(regexp = "^\\d{6}$")
    String simplePassword
) {

}
