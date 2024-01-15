package kr.co.fastcampus.yanabada.domain.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import kr.co.fastcampus.yanabada.domain.member.entity.ProviderType;

public record OauthSignUpRequest(
    @NotEmpty(message = "이메일 주소가 비어있을 수 없습니다")
    @Email
    String email,

    @NotEmpty(message = "사용자 이름이 비어있을 수 없습니다")
    String memberName,
    @NotEmpty(message = "닉네임이 비어있을 수 없습니다")
    String nickName,
    @NotEmpty(message = "휴대 전화 번호가 비어있을 수 없습니다")
    String phoneNumber,

    ProviderType provider,
    String deviceKey

) {
}
