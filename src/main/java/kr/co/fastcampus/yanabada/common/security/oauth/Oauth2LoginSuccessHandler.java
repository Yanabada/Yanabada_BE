package kr.co.fastcampus.yanabada.common.security.oauth;

import static kr.co.fastcampus.yanabada.domain.member.entity.ProviderType.KAKAO;
import static kr.co.fastcampus.yanabada.domain.member.entity.RoleType.ROLE_USER;
import static org.springframework.http.HttpStatus.OK;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import kr.co.fastcampus.yanabada.common.jwt.dto.TokenIssueResponse;
import kr.co.fastcampus.yanabada.common.jwt.service.TokenService;
import kr.co.fastcampus.yanabada.common.jwt.util.JwtProvider;
import kr.co.fastcampus.yanabada.domain.auth.dto.request.LoginRequest;
import kr.co.fastcampus.yanabada.domain.auth.dto.response.LoginResponse;
import kr.co.fastcampus.yanabada.domain.auth.service.AuthService;
import kr.co.fastcampus.yanabada.domain.member.entity.ProviderType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class Oauth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final TokenService tokenService;
    private final AuthService authService;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public void onAuthenticationSuccess(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication
    ) throws IOException {

        DefaultOAuth2User defaultOauth2 = (DefaultOAuth2User) authentication.getPrincipal();
        Map<String, Object> attribute = defaultOauth2.getAttributes();

        boolean isExist = (boolean) attribute.get("isExist");
        String email = (String) attribute.get("email");
        String provider = (String) attribute.get("provider");

        if (isExist) {
            /* 바로 로그인 */
            String oauthPassword = "oauth-password";    //todo: 환경 변수 분리
            LoginRequest loginRequest = new LoginRequest(email, oauthPassword);
            LoginResponse loginResponse
                = authService.loginOauth(loginRequest, ProviderType.valueOf(provider));

            String loginResponseJson = objectMapper.writeValueAsString(loginResponse);
            response.setStatus(OK.value());
            response.getWriter().write(loginResponseJson);
        } else {
            /* 회원 가입 필요 */
            //todo: url 변경 예정, 환경 변수(서버, 로컬) 분리 예정
            String redirectUrl = "http://localhost:8080/redirect-url"
                + "?email=" + attribute.get("email")
                + "&provider=" + attribute.get("provider");
            response.sendRedirect(redirectUrl);
        }

    }
}
