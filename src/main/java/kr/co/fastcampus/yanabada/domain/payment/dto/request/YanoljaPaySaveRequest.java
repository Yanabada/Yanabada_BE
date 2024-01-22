package kr.co.fastcampus.yanabada.domain.payment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record YanoljaPaySaveRequest(
    @NotBlank(message = "비밀번호는 필수로 입력하셔야 합니다.")
    @Pattern(regexp = "^\\d{6}$", message = "비밀번호는 6자리 숫자이어야 합니다.")
    String simplePassword,

    @NotBlank(message = "은행 이름은 필수로 입력하셔야 합니다.")
    String bankName,

    @NotBlank(message = "계좌번호는 필수로 입력하셔야 합니다.")
    @Pattern(regexp = "^\\d{10,14}$", message = "계좌번호는 10-14자리 숫자이어야 합니다.")
    String accountNumber
) {

}
