package kr.co.fastcampus.yanabada.domain.member.controller;

import kr.co.fastcampus.yanabada.common.response.ResponseBody;
import kr.co.fastcampus.yanabada.common.security.PrincipalDetails;
import kr.co.fastcampus.yanabada.domain.member.dto.request.EmailDuplCheckRequest;
import kr.co.fastcampus.yanabada.domain.member.dto.request.ImgUrlChangeRequest;
import kr.co.fastcampus.yanabada.domain.member.dto.request.NickNameChangeRequest;
import kr.co.fastcampus.yanabada.domain.member.dto.request.NickNameDuplCheckRequest;
import kr.co.fastcampus.yanabada.domain.member.dto.request.PasswordChangeRequest;
import kr.co.fastcampus.yanabada.domain.member.dto.request.PhoneNumberChangeRequest;
import kr.co.fastcampus.yanabada.domain.member.dto.response.DuplCheckResponse;
import kr.co.fastcampus.yanabada.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/test")
    public String test(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        log.info("email={}", principalDetails.email());
        log.info("providerType={}", principalDetails.provider());
        log.info("provider={}", principalDetails.provider().name());
        return "test";
    }

    @PutMapping("/password")
    public ResponseBody<Void> modifyPassword(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @RequestBody PasswordChangeRequest passwordRequest
    ) {
        memberService.modifyPassword(
            passwordRequest, principalDetails.email(), principalDetails.provider()
        );
        return ResponseBody.ok();
    }

    @PutMapping("/nickname")
    public ResponseBody<Void> modifyNickName(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @RequestBody NickNameChangeRequest nickNameRequest
    ) {
        memberService.modifyNickName(
            nickNameRequest, principalDetails.email(), principalDetails.provider()
        );
        return ResponseBody.ok();
    }

    @PutMapping("/phone-number")
    public ResponseBody<Void> modifyPhoneNumber(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @RequestBody PhoneNumberChangeRequest phoneNumberRequest
    ) {
        memberService.modifyPhoneNumber(
            phoneNumberRequest, principalDetails.email(), principalDetails.provider()
        );
        return ResponseBody.ok();
    }

    @PutMapping("/image")
    public ResponseBody<Void> modifyImage(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @RequestBody ImgUrlChangeRequest imgUrlRequest
    ) {
        log.info("imgRequest={}", imgUrlRequest.imageUrl());
        memberService.modifyImageUrl(
            imgUrlRequest, principalDetails.email(), principalDetails.provider()
        );
        return ResponseBody.ok();
    }

    @PostMapping("/email/duplication-check")
    public ResponseBody<DuplCheckResponse> checkDuplEmail(
        @RequestBody EmailDuplCheckRequest emailRequest
    ) {
        return ResponseBody.ok(memberService.isExistEmail(emailRequest));
    }

    @PostMapping("/nickname/duplication-check")
    public ResponseBody<DuplCheckResponse> checkDuplNickName(
        @RequestBody NickNameDuplCheckRequest nickNameRequest
    ) {
        return ResponseBody.ok(memberService.isExistNickName(nickNameRequest));
    }

    @PostMapping("/email/verification")
    public void verifyEmaAil() {
    }

    @PostMapping("/phone-number/verification")
    public void verifyPhoneNumber() {
    }

}
