package kr.co.fastcampus.yanabada.common.utils;

import static kr.co.fastcampus.yanabada.common.jwt.constant.JwtConstant.REFRESH_TOKEN_EXPIRE_TIME;

import jakarta.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import kr.co.fastcampus.yanabada.common.jwt.dto.TokenIssueResponse;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import org.springframework.http.ResponseCookie;

public class CookieCreator {

    public static void storeLoginResponse(
        HttpServletResponse response,
        TokenIssueResponse tokenIssue,
        Member member
    ) {
        setValueInCookie(response, "isLoggedIn", "yes");
        setValueInCookie(response, "accessToken", tokenIssue.accessToken());
        setValueInCookie(response, "refreshToken", tokenIssue.refreshToken());
        setValueInCookie(response, "id", String.valueOf(member.getId()));
        setValueInCookie(response, "email", String.valueOf(member.getEmail()));
        setValueInCookie(response, "nickName", String.valueOf(member.getNickName()));
        setValueInCookie(response, "image", String.valueOf(member.getImage()));
        setValueInCookie(response, "provider", String.valueOf(member.getProviderType()));
    }

    public static void storeOauth2Attribute(
        HttpServletResponse response,
        String email, String provider
    ) {
        setValueInCookie(response, "email", String.valueOf(email));
        setValueInCookie(response, "provider", String.valueOf(provider));
    }

    private static void setValueInCookie(
        HttpServletResponse response, String key, String value
    ) {
        try {
            ResponseCookie cookie = ResponseCookie
                .from(key, URLEncoder.encode(value, "UTF-8"))
                .secure(true)
                .path("/")
                .sameSite("None")
                .maxAge(REFRESH_TOKEN_EXPIRE_TIME)
                .domain("yanabada.com")
                .build();
            response.addHeader("Set-Cookie", cookie.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
