package kr.co.fastcampus.yanabada.domain.member.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record NickNameDuplCheckRequest(
    @NotEmpty(message = "닉네임이 비어있을 수 없습니다")
    String nickName
) {
}
