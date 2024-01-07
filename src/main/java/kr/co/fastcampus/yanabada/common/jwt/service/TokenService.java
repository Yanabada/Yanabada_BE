package kr.co.fastcampus.yanabada.common.jwt.service;

import kr.co.fastcampus.yanabada.common.redis.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static kr.co.fastcampus.yanabada.common.jwt.constant.JwtConstant.REFRESH_TOKEN_EXPIRE_TIME;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final RedisUtils redisUtils;

    @Transactional
    public void saveRefreshToken(
            String email,
            String provider,
            String refreshToken
    ) {
        String value = email + " " + provider;
        redisUtils.setData(refreshToken, value, REFRESH_TOKEN_EXPIRE_TIME);
    }

    @Transactional
    public String getValue(String refreshToken) {
        return redisUtils.getData(refreshToken);
    }

    @Transactional
    public void deleteRefreshToken(String refreshToken) {
        redisUtils.deleteData(refreshToken);
    }

}
