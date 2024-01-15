package kr.co.fastcampus.yanabada.common.security.oauth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import kr.co.fastcampus.yanabada.common.jwt.dto.TokenIssueResponse;
import kr.co.fastcampus.yanabada.common.jwt.service.TokenService;
import kr.co.fastcampus.yanabada.common.jwt.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    @Transactional
    public void onAuthenticationSuccess(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication
    ) throws IOException, ServletException {

        DefaultOAuth2User defaultOauth2 = (DefaultOAuth2User) authentication.getPrincipal();
        Map<String, Object> attribute = defaultOauth2.getAttributes();

        log.info("OAuth2LoginSuccessHandler email={}", attribute.get("email"));
        log.info("OAuth2LoginSuccessHandler name={}", attribute.get("name"));
        log.info("OAuth2LoginSuccessHandler provider={}", attribute.get("provider"));
        log.info("OAuth2LoginSuccessHandler isExist={}", attribute.get("isExist"));

        boolean isExist = (boolean) attribute.get("isExist");

        if (isExist) {
           /* 바로 로그인 */
//            TokenIssueResponse tokenIssue =
        } else {
            /* 회원 가입 필요 */
            StringBuilder redirectUrl = new StringBuilder();
            //todo: url 변경 예정, 환경 변수(서버, 로컬) 분리 예정
            redirectUrl.append("http://localhost:8080/redirect-url")
                .append("?email=").append(attribute.get("email"))
                .append("&name=").append(attribute.get("name"))
                .append("&provider=").append(attribute.get("provider"));
        }

    }
}
