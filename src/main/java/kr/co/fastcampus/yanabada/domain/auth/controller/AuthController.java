package kr.co.fastcampus.yanabada.domain.auth.controller;

import static kr.co.fastcampus.yanabada.common.jwt.constant.JwtConstant.AUTHORIZATION_HEADER;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import kr.co.fastcampus.yanabada.common.jwt.dto.TokenRefreshResponse;
import kr.co.fastcampus.yanabada.common.jwt.util.JwtUtils;
import kr.co.fastcampus.yanabada.common.response.ResponseBody;
import kr.co.fastcampus.yanabada.domain.auth.dto.request.AuthCodeVerifyRequest;
import kr.co.fastcampus.yanabada.domain.auth.dto.request.EmailCodeSendRequest;
import kr.co.fastcampus.yanabada.domain.auth.dto.request.LoginRequest;
import kr.co.fastcampus.yanabada.domain.auth.dto.request.OauthSignUpRequest;
import kr.co.fastcampus.yanabada.domain.auth.dto.request.SignUpRequest;
import kr.co.fastcampus.yanabada.domain.auth.dto.response.AuthCodeVerifyResponse;
import kr.co.fastcampus.yanabada.domain.auth.dto.response.SignUpResponse;
import kr.co.fastcampus.yanabada.domain.auth.service.AuthService;
import kr.co.fastcampus.yanabada.domain.member.dto.request.EmailDuplCheckRequest;
import kr.co.fastcampus.yanabada.domain.member.dto.request.NickNameDuplCheckRequest;
import kr.co.fastcampus.yanabada.domain.member.dto.response.DuplCheckResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseBody<SignUpResponse> signUp(
        @RequestBody @Valid SignUpRequest signUpRequest
    ) {
        return ResponseBody.ok(authService.signUp(signUpRequest));
    }

    @PostMapping("/sign-up/social")
    public ResponseBody<SignUpResponse> oauthSignUp(
        @RequestBody @Valid OauthSignUpRequest signUpRequest
    ) {
        return ResponseBody.ok(authService.oauthSignUp(signUpRequest));
    }

    @PostMapping("/login")
    public ResponseBody<Void> login(
        HttpServletResponse response,
        @RequestBody @Valid LoginRequest loginRequest
    ) {
        authService.login(response, loginRequest);
        return ResponseBody.ok();
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

    @PostMapping("/duplication/nickname")
    public ResponseBody<DuplCheckResponse> checkfDuplNickName(
        @RequestBody @Valid NickNameDuplCheckRequest nickNameRequest
    ) {
        return ResponseBody.ok(authService.isExistNickName(nickNameRequest));
    }

    @PostMapping("/verification-code/email/signup")
    public ResponseBody<Void> sendAuthCodeToEmailForSignUp(
        @RequestBody @Valid EmailCodeSendRequest emailCodeSendRequest
    ) {
        authService.sendAuthCodeToEmailForSignUp(emailCodeSendRequest);
        return ResponseBody.ok();
    }

    @PostMapping("/verification-code/email/password")
    public ResponseBody<Void> sendAuthCodeToEmailForPwdModify(
        @RequestBody @Valid EmailCodeSendRequest emailCodeSendRequest
    ) {
        authService.sendAuthCodeToEmailForPwdModify(emailCodeSendRequest);
        return ResponseBody.ok();
    }

    @PostMapping("/verification/email")
    public ResponseBody<AuthCodeVerifyResponse> verifyAuthCodeForEmail(
        @RequestBody @Valid AuthCodeVerifyRequest codeRequest
    ) {
        return ResponseBody.ok(authService.verifyAuthCode(codeRequest));
    }

    @PostMapping("/verification/phone")
    public void verifyPhoneNumber() {
    }

}
