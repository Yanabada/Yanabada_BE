package kr.co.fastcampus.yanabada.domain.auth.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import kr.co.fastcampus.yanabada.domain.member.entity.ProviderType;
import lombok.NonNull;

public record OauthSignUpRequest(
    @NotEmpty(message = "이메일 주소가 비어있을 수 없습니다")
    @Pattern(
        regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$",
        message = "이메일 형식으로 입력해주세요."
    )
    String email,
    @NotEmpty(message = "닉네임이 비어있을 수 없습니다")
    String nickName,
    @NotEmpty(message = "휴대 전화 번호가 비어있을 수 없습니다")
    String phoneNumber,
    @NonNull
    ProviderType provider
) {
}
