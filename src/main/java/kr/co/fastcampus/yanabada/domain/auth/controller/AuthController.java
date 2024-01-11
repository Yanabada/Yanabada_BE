package kr.co.fastcampus.yanabada.domain.auth.controller;

import static kr.co.fastcampus.yanabada.common.jwt.constant.JwtConstant.AUTHORIZATION_HEADER;
import static kr.co.fastcampus.yanabada.common.jwt.constant.JwtConstant.BEARER_PREFIX;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.fastcampus.yanabada.common.jwt.dto.TokenIssueResponse;
import kr.co.fastcampus.yanabada.common.jwt.dto.TokenRefreshResponse;
import kr.co.fastcampus.yanabada.common.response.ResponseBody;
import kr.co.fastcampus.yanabada.domain.auth.dto.LoginRequest;
import kr.co.fastcampus.yanabada.domain.auth.dto.SignUpRequest;
import kr.co.fastcampus.yanabada.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseBody<Long> signUp(@RequestBody SignUpRequest signUpRequest) {
        return ResponseBody.ok(authService.signUp(signUpRequest));
    }

    @PostMapping("/login")
    public ResponseBody<TokenIssueResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseBody.ok(authService.login(loginRequest));
    }

    @PostMapping("/logout")
    public ResponseBody<Void> logout(
        @RequestHeader(AUTHORIZATION_HEADER) final String refreshToken
    ) {
        authService.logout(extractTokenFromRawToken(refreshToken));
        return ResponseBody.ok();
    }

    @PostMapping("/refresh-token")
    public ResponseBody<TokenRefreshResponse> refreshToken(
        @RequestHeader(AUTHORIZATION_HEADER) final String refreshToken
    ) {
        return ResponseBody.ok(
            authService.generateNewAccessToken(extractTokenFromRawToken(refreshToken))
        );
    }

    private String extractTokenFromRawToken(String rawToken) {
        if (StringUtils.hasText(rawToken) && rawToken.startsWith(BEARER_PREFIX)) {
            return rawToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }   //todo: JwtUtils와 코드 중복인데, 어떻게 처리할 지 고민

}
