package kr.co.fastcampus.yanabada.domain.member.controller;

import kr.co.fastcampus.yanabada.common.redis.RedisUtils;
import kr.co.fastcampus.yanabada.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final RedisUtils redisUtils;
    private final StringRedisTemplate stringStringRedisTemplate;

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/test/member")
    public String testMember() {
        memberService.saveMember();
        return "test";
    }

    @GetMapping("/test/redis/utils")
    public String testRedisUtils() {
        redisUtils.setData("hi", "hihihi", 60000L);
        ValueOperations<String, String> stringValueOperations = stringStringRedisTemplate.opsForValue();
        stringValueOperations.set("hihi", "hihivalue");
        return "test-redis-utils";
    }

    @GetMapping("/test/redis/get/{key}")
    public String getTest(@PathVariable("key") String key) {
        log.info("key={}", key);
        ValueOperations<String, String> stringValueOperations = stringStringRedisTemplate.opsForValue();
        String value = stringValueOperations.get(key);
        if(value==null) {
            value = redisUtils.getData(key);
        }
        if(value==null) return "null";
        return value;
    }



}
