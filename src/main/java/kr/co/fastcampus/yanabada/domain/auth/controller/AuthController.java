package kr.co.fastcampus.yanabada.domain.auth.controller;

import static kr.co.fastcampus.yanabada.common.jwt.constant.JwtConstant.AUTHORIZATION_HEADER;

import jakarta.validation.Valid;
import kr.co.fastcampus.yanabada.common.jwt.dto.TokenIssueResponse;
import kr.co.fastcampus.yanabada.common.jwt.dto.TokenRefreshResponse;
import kr.co.fastcampus.yanabada.common.jwt.util.JwtUtils;
import kr.co.fastcampus.yanabada.common.response.ResponseBody;
import kr.co.fastcampus.yanabada.domain.auth.dto.request.EmailAuthCodeRequest;
import kr.co.fastcampus.yanabada.domain.auth.dto.request.LoginRequest;
import kr.co.fastcampus.yanabada.domain.auth.dto.request.SignUpRequest;
import kr.co.fastcampus.yanabada.domain.auth.dto.response.EmailAuthCodeResponse;
import kr.co.fastcampus.yanabada.domain.auth.service.AuthService;
import kr.co.fastcampus.yanabada.domain.auth.service.MailAuthService;
import kr.co.fastcampus.yanabada.domain.member.dto.request.EmailDuplCheckRequest;
import kr.co.fastcampus.yanabada.domain.member.dto.request.NickNameDuplCheckRequest;
import kr.co.fastcampus.yanabada.domain.member.dto.response.DuplCheckResponse;
import kr.co.fastcampus.yanabada.domain.member.service.MemberService;
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
    private final MemberService memberService;
    private final MailAuthService mailAuthService;

    @PostMapping("/sign-up")
    public ResponseBody<Long> signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        return ResponseBody.ok(authService.signUp(signUpRequest));
    }

    @PostMapping("/login")
    public ResponseBody<TokenIssueResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseBody.ok(authService.login(loginRequest));
    }

    @PostMapping("/logout")
    public ResponseBody<Void> logout(
        @RequestHeader(AUTHORIZATION_HEADER) final String refreshToken
    ) {
        authService.logout(JwtUtils.extractTokenFromRawToken(refreshToken));
        return ResponseBody.ok();
    }

    @PostMapping("/refresh-token")
    public ResponseBody<TokenRefreshResponse> refreshToken(
        @RequestHeader(AUTHORIZATION_HEADER) final String refreshToken
    ) {
        return ResponseBody.ok(
            authService.generateNewAccessToken(JwtUtils.extractTokenFromRawToken(refreshToken))
        );
    }

    @PostMapping("/duplication/email")
    public ResponseBody<DuplCheckResponse> checkDuplEmail(
        @RequestBody EmailDuplCheckRequest emailRequest
    ) {
        return ResponseBody.ok(memberService.isExistEmail(emailRequest));
    }

    @PostMapping("/duplication/nickname")
    public ResponseBody<DuplCheckResponse> checkDuplNickName(
        @RequestBody NickNameDuplCheckRequest nickNameRequest
    ) {
        return ResponseBody.ok(memberService.isExistNickName(nickNameRequest));
    }

    @PostMapping("/verification/code/email")
    public ResponseBody<EmailAuthCodeResponse> verifyEmail(
        @RequestBody EmailAuthCodeRequest emailAuthCodeRequest
    ) {
        return ResponseBody.ok(mailAuthService.sendEmail(emailAuthCodeRequest));
    }

    @PostMapping("/verification/code/phone")
    public void verifyPhoneNumber() {
    }

}
