package kr.co.fastcampus.yanabada.domain.auth.dto.request;

public record AuthCodeDto(
    String code,
    Boolean isVerified
) {
}
