package kr.co.fastcampus.yanabada.domain.auth.dto.request;

public record SignUpRequest(
    String email,
    String password,
    String memberName,
    String nickName,
    String phoneNumber,
    String deviceKey

) {
}
