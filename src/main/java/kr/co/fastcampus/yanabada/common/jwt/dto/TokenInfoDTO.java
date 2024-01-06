package kr.co.fastcampus.yanabada.common.jwt.dto;

public record TokenInfoDTO (
    String accessToken,
    String refreshToken
) {
}