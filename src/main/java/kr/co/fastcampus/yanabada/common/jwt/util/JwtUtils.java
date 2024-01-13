package kr.co.fastcampus.yanabada.common.jwt.util;

import static kr.co.fastcampus.yanabada.common.jwt.constant.JwtConstant.BEARER_PREFIX;

import org.springframework.util.StringUtils;

public class JwtUtils {

    public static String extractTokenFromRawToken(String rawToken) {
        if (StringUtils.hasText(rawToken) && rawToken.startsWith(BEARER_PREFIX)) {
            return rawToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }
}
