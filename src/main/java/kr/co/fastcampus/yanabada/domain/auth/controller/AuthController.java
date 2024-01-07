package kr.co.fastcampus.yanabada.domain.auth.controller;

import kr.co.fastcampus.yanabada.common.jwt.service.TokenService;
import kr.co.fastcampus.yanabada.common.jwt.util.JwtProvider;
import kr.co.fastcampus.yanabada.domain.auth.dto.LoginRequest;
import kr.co.fastcampus.yanabada.domain.auth.dto.SignUpRequest;
import kr.co.fastcampus.yanabada.domain.auth.service.AuthService;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.member.entity.ProviderType;
import kr.co.fastcampus.yanabada.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import static kr.co.fastcampus.yanabada.common.jwt.constant.JwtConstant.AUTHORIZATION_HEADER;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;
    private final TokenService tokenService;
    private final JwtProvider jwtProvider;
    private final MemberService memberService;

    @PostMapping("/sign-up")
    public String signUp(@RequestBody SignUpRequest signUpRequest) {
        authService.signUp(signUpRequest);
        return "sign-up-success";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("token/logout")
    public String logout(@RequestHeader(AUTHORIZATION_HEADER) final String refreshToken) {

        tokenService.deleteRefreshToken(refreshToken);
        return "success";   //todo: return값 변경 예정
    }

    @PostMapping("/token/refresh")
    public String refresh(@RequestHeader(AUTHORIZATION_HEADER) final String refreshToken) {

        log.info("token = {}", refreshToken);

        if(StringUtils.hasText(refreshToken) && jwtProvider.verifyToken(refreshToken)) {
            String value = tokenService.getValue(refreshToken);
            if(value==null) {
                throw new RuntimeException("RefreshToken이 만료되었습니다.");  //todo
            }
            String[] splits = value.split(" ");
            String email = splits[0];
            ProviderType provider = ProviderType.valueOf(splits[1]);
            Member findMember = memberService.findMember(email, provider);

            return jwtProvider.generateAccessToken(
                    findMember.getEmail(),
                    findMember.getRoleType().name(),
                    findMember.getProviderType().name()
            );
        }

        return "Not Valid refreshToken"; //todo: 반환 값 변경 예정
    }

}
