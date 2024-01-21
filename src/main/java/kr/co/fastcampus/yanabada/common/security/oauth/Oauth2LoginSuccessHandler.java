package kr.co.fastcampus.yanabada.common.security.oauth;

import static org.springframework.http.HttpStatus.OK;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import kr.co.fastcampus.yanabada.common.jwt.service.TokenService;
import kr.co.fastcampus.yanabada.common.jwt.util.JwtProvider;
import kr.co.fastcampus.yanabada.domain.auth.dto.request.LoginRequest;
import kr.co.fastcampus.yanabada.domain.auth.dto.response.LoginResponse;
import kr.co.fastcampus.yanabada.domain.auth.service.AuthService;
import kr.co.fastcampus.yanabada.domain.member.entity.ProviderType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class Oauth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final AuthService authService;
    private final ObjectMapper objectMapper;
    @Value("${spring.login.root-url}")
    String rootUrl;
    @Value("${spring.login.oauth2-redirect-url}")
    String oauthRedirectUrl;
    @Value("${spring.login.oauth2-password}")
    String oauthPassword;

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
            LoginRequest loginRequest = new LoginRequest(email, oauthPassword);
            LoginResponse loginResponse
                = authService.loginOauth(response, loginRequest, ProviderType.valueOf(provider));

            String loginResponseJson = objectMapper.writeValueAsString(loginResponse);
            response.setStatus(OK.value());
            response.getWriter().write(loginResponseJson);
        } else {
            /* 회원 가입 필요 */
            //todo: url 변경 예정
            String redirectUrl = rootUrl
                + oauthRedirectUrl
                + "?email=" + attribute.get("email")
                + "&provider=" + attribute.get("provider");
            response.sendRedirect(redirectUrl);
        }

    }
}
