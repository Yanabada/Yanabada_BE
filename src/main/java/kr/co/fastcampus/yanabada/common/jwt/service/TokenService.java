package kr.co.fastcampus.yanabada.common.jwt.service;

import static kr.co.fastcampus.yanabada.common.jwt.constant.JwtConstant.REFRESH_TOKEN_EXPIRE_TIME;

import kr.co.fastcampus.yanabada.common.jwt.dto.TokenIssueResponse;
import kr.co.fastcampus.yanabada.common.redis.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final RedisUtils<TokenIssueResponse> redisUtils;

    public void saveTokenIssue(
        String email,
        String provider,
        TokenIssueResponse tokenIssue
    ) {
        String key = email + " " + provider;
        redisUtils.setDataAsHash(key, tokenIssue, 300000L);
        //todo : exprie시간 변경 예정
    }

    public TokenIssueResponse getTokenIssue(
        String email,
        String provider
    ) {
        String key = email + " " + provider;
        return redisUtils.getDataAsHash(key);
    }

    public boolean isExistToken(String email, String provider) {
        return getTokenIssue(email, provider) != null;
    }

    public void updateAccessToken(
        String email,
        String provider,
        String newAccessToken
    ) {
        String key = email + " " + provider;
        TokenIssueResponse tokenIssue = redisUtils.getDataAsHash(key);
        TokenIssueResponse newTokenIssue = TokenIssueResponse.builder()
            .accessToken(newAccessToken)
            .refreshToken(tokenIssue.refreshToken())
            .build();
        redisUtils.setDataAsHash(key, newTokenIssue, REFRESH_TOKEN_EXPIRE_TIME);
    }

    public void deleteToken(String email, String provider) {
        String key = email +" " + provider;
        redisUtils.deleteData(key);
    }

}
