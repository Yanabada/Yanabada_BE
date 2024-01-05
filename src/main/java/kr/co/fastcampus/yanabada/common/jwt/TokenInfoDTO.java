package kr.co.fastcampus.yanabada.common.jwt;

public record TokenInfoDTO (
    String accessToken,
    String refreshToken
) {
}