package kr.co.fastcampus.yanabada.common.jwt.dto;

public record TokenIssueResponse(
    String accessToken,
    String refreshToken
) {
}