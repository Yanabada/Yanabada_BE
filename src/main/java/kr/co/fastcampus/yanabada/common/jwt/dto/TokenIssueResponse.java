package kr.co.fastcampus.yanabada.common.jwt.dto;

import lombok.Builder;

@Builder
public record TokenIssueResponse(
    String accessToken,
    String refreshToken
) {
}