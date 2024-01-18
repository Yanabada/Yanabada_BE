package kr.co.fastcampus.yanabada.domain.auth.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record SignUpRequest(
    @NotEmpty(message = "이메일 주소가 비어있을 수 없습니다")
    @Pattern(
        regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$",
        message = "이메일 형식으로 입력해주세요."
    )
    String email,
    @NotEmpty(message = "패스워드가 비어있을 수 없습니다")
    @Pattern(
        regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
        message = "패스워드는 영문+숫자+특수문자 8~20 자리이어야 합니다."
    )
    String password,
    @NotEmpty(message = "닉네임이 비어있을 수 없습니다")
    String nickName,
    @NotEmpty(message = "휴대 전화 번호가 비어있을 수 없습니다")
    String phoneNumber,
    String deviceKey

) {
}
