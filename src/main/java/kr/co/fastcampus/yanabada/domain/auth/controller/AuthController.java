package kr.co.fastcampus.yanabada.domain.auth.controller;

import static kr.co.fastcampus.yanabada.common.jwt.constant.JwtConstant.AUTHORIZATION_HEADER;

import kr.co.fastcampus.yanabada.common.jwt.dto.TokenIssueResponse;
import kr.co.fastcampus.yanabada.common.jwt.dto.TokenRefreshResponse;
import kr.co.fastcampus.yanabada.common.response.ResponseBody;
import kr.co.fastcampus.yanabada.domain.auth.dto.LoginRequest;
import kr.co.fastcampus.yanabada.domain.auth.dto.SignUpRequest;
import kr.co.fastcampus.yanabada.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseBody<Long> signUp(@RequestBody SignUpRequest signUpRequest) {
        return ResponseBody.ok(authService.signUp(signUpRequest));
    }

    @PostMapping("/login")
    public ResponseBody<TokenIssueResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseBody.ok(authService.login(loginRequest));
    }

    @PostMapping("/logout")
    public ResponseBody<Void> logout(@RequestHeader(AUTHORIZATION_HEADER) final String refreshToken) {

        authService.logout(refreshToken);
        return ResponseBody.ok();
    }

//    @PostMapping("/refresh-token")
//    public ResponseBody<TokenRefreshResponse> refreshToken(
//        @RequestHeader(AUTHORIZATION_HEADER) final String refreshToken
//    ) {
//
//    }

//    @PostMapping("/token/refresh")
//    public String refresh(@RequestHeader(AUTHORIZATION_HEADER) final String refreshToken) {
//
//        log.info("token = {}", refreshToken);
//
//        if (StringUtils.hasText(refreshToken) && jwtProvider.verifyToken(refreshToken)) {
//            String value = tokenService.getValue(refreshToken);
//            if (value == null) {
//                throw new TokenExpiredException();  //todo: ControllerAdvice에서 핸들러 처리
//            }
//            String[] splits = value.split(" ");
//            String email = splits[0];
//            ProviderType provider = ProviderType.valueOf(splits[1]);
//            Member findMember = memberService.findMember(email, provider);
//
//            return jwtProvider.generateAccessToken(
//                    findMember.getEmail(),
//                    findMember.getRoleType().name(),
//                    findMember.getProviderType().name()
//            );
//        }
//
//        return "Not Valid refreshToken"; //todo: 반환 값 변경 예정
//    }

}
