package kr.co.fastcampus.yanabada.domain.auth.dto.request;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public record LoginRequest(
    @NotEmpty(message = "이메일 주소가 비어있을 수 없습니다")
    String email,
    @NotEmpty(message = "패스워드가 비어있을 수 없습니다")
    String password
) {
    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
