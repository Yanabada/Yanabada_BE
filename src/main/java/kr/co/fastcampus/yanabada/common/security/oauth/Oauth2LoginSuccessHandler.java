package kr.co.fastcampus.yanabada.common.security.oauth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import kr.co.fastcampus.yanabada.common.utils.CookieCreator;
import kr.co.fastcampus.yanabada.domain.auth.dto.request.LoginRequest;
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
    @Value("${spring.login.app-home-url}")
    String appUrl;
    @Value("${spring.login.oauth2-redirect-url}")
    String redirectPath;
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
            authService.loginOauth(response, loginRequest, ProviderType.valueOf(provider));
            response.sendRedirect(appUrl + "/");
        } else {
            /* 회원 가입 필요 */
            String redirectUrl = appUrl
                + redirectPath
                + "?email=" + email
                + "&provider=" + provider;
            CookieCreator.storeOauth2Attribute(response, email, provider);
            response.sendRedirect(redirectUrl);
        }

    }
}
