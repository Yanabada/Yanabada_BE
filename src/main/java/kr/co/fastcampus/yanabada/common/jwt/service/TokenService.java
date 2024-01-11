package kr.co.fastcampus.yanabada.common.jwt.service;

import static kr.co.fastcampus.yanabada.common.jwt.constant.JwtConstant.REFRESH_TOKEN_EXPIRE_TIME;

import kr.co.fastcampus.yanabada.common.jwt.dto.TokenIssueResponse;
import kr.co.fastcampus.yanabada.common.redis.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final RedisUtils<TokenIssueResponse> redisUtils;

    public void saveTokenIssue(
        String email,
        String provider,
        TokenIssueResponse tokenIssue
    ) {
        redisUtils.setDataAsHash(
            getKey(email, provider),
            tokenIssue,
            REFRESH_TOKEN_EXPIRE_TIME
        );
    }

    public TokenIssueResponse getTokenIssue(
        String email,
        String provider
    ) {
        return redisUtils.getDataAsHash(getKey(email, provider));
    }

    public boolean isExistToken(String email, String provider) {
        return getTokenIssue(email, provider) != null;
    }

    public void updateAccessToken(
        String email,
        String provider,
        String newAccessToken
    ) {
        String key = getKey(email, provider);
        TokenIssueResponse tokenIssue = redisUtils.getDataAsHash(key);
        TokenIssueResponse newTokenIssue = TokenIssueResponse.builder()
            .accessToken(newAccessToken)
            .refreshToken(tokenIssue.refreshToken())
            .build();
        redisUtils.setDataAsHash(key, newTokenIssue, REFRESH_TOKEN_EXPIRE_TIME);
    }

    public void deleteToken(String email, String provider) {
        redisUtils.deleteData(getKey(email, provider));
    }

    private String getKey(String email, String provider) {
        return email + " " + provider;
    }

}
