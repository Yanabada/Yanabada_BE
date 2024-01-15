package kr.co.fastcampus.yanabada.domain.auth.dto.request;

import kr.co.fastcampus.yanabada.domain.member.entity.ProviderType;

public record OauthSignUpRequest(
    String email,
    String memberName,
    String nickName,
    String phoneNumber,
    ProviderType provider,
    String deviceKey

) {
}
