package kr.co.fastcampus.yanabada.domain.payment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record PayPasswordRequest(
    @NotBlank(message = "패스워드는 공백일 수 없습니다.")
    @Pattern(regexp = "\\d{6}", message = "패스워드는 6글자이어야 합니다.")
    String password,
    @NotBlank(message = "확인용 패스워드도 공백일 수 없습니다.")
    String confirmPassword
) {

    public boolean isPasswordMatch() {
        return this.password.equals(this.confirmPassword);
    }
}
