package kr.co.fastcampus.yanabada.domain.auth.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record EmailAuthCodeRequest(
    @NotEmpty(message = "이메일 주소가 비어있을 수 없습니다")
    String email
) {
}