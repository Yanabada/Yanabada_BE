package kr.co.fastcampus.yanabada.domain.payment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record YanoljaPaySaveRequest(
    @NotBlank
    @Pattern(regexp = "^\\d{6}$")
    String simplePassword,

    @NotBlank
    String bankName,

    @NotBlank
    @Pattern(regexp = "^\\d{10,14}$")
    String accountNumber
) {

}
