package kr.co.fastcampus.yanabada.domain.member.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record FcmTokenRegisterRequest(
    @NotEmpty(message = "토큰 값이 비어있을 수 없습니다")
    String fcmToken
) {

}