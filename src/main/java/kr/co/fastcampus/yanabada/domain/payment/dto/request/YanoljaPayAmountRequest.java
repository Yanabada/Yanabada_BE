package kr.co.fastcampus.yanabada.domain.payment.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record YanoljaPayAmountRequest(
    @NotNull(message = "금액은 필수로 입력하셔야 합니다.")
    @Positive(message = "금액은 양수이어야 합니다.")
    Long amount,

    @NotEmpty(message = "비밀번호는 필수로 입력하셔야 합니다.")
    @Pattern(regexp = "^\\d{6}$", message = "비밀번호는 6자리 숫자이어야 합니다.")
    String simplePassword
) {

}
