package kr.co.fastcampus.yanabada.domain.auth.dto;

public record SignUpRequest (
    String email,
    String password,
    String memberName,
    String nickName,
    String phoneNumber,
    String deviceKey

) {
}
