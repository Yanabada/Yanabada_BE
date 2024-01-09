package kr.co.fastcampus.yanabada.domain.member.controller;

import kr.co.fastcampus.yanabada.common.jwt.dto.TokenIssueResponse;
import kr.co.fastcampus.yanabada.common.jwt.service.TokenService;
import kr.co.fastcampus.yanabada.common.redis.RedisUtils;
import kr.co.fastcampus.yanabada.domain.member.entity.ProviderType;
import kr.co.fastcampus.yanabada.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final TokenService tokenService;

    @GetMapping("/redis/set")
    public String set() {
        String email = "test@test.com";
        String provider = ProviderType.EMAIL.name();
        TokenIssueResponse tokenIssueResponse
            = new TokenIssueResponse("act", "rft");
        tokenService.saveTokenIssue(email, provider, tokenIssueResponse);
        return "set_Success";
    }

    @GetMapping("/redis/get")
    public TokenIssueResponse get() {
        String email = "test@test.com";
        String provider = ProviderType.EMAIL.name();
        return tokenService.getTokenIssue(email, provider);
    }

    @GetMapping("/redis/delete")
    public String delete() {
        String email = "test@test.com";
        String provider = ProviderType.EMAIL.name();
        tokenService.deleteToken(email, provider);
        return "delete_Success";
    }

}
