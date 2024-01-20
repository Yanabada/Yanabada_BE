package kr.co.fastcampus.yanabada.domain.member.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record ImageModifyRequest(
    @NotEmpty(message = "패스워드가 비어있을 수 없습니다")
    String image
) {
}

