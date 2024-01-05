package kr.co.fastcampus.yanabada.common.jwt;

public class JwtConstant {
    public static final String BEARER_TYPE = "Bearer";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;           // 30분 1000 * 60 * 30
    public static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일 1000 * 60 * 60 * 24 * 7
    public static final String AUTHORIZATION_HEADER = "Authorization";
}
