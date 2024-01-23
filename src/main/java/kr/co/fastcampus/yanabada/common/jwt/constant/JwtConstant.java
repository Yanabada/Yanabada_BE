package kr.co.fastcampus.yanabada.common.jwt.constant;

public class JwtConstant {
    public static final String BEARER_PREFIX = "Bearer ";
    public static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24;  //1day(릴리즈 시 30min)
    public static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  //7days
    public static final String AUTHORIZATION_HEADER = "Authorization";
}
