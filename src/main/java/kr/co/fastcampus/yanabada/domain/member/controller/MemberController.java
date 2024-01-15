package kr.co.fastcampus.yanabada.domain.member.controller;

import kr.co.fastcampus.yanabada.common.response.ResponseBody;
import kr.co.fastcampus.yanabada.common.security.PrincipalDetails;
import kr.co.fastcampus.yanabada.domain.member.dto.request.ImgUrlModifyRequest;
import kr.co.fastcampus.yanabada.domain.member.dto.request.NickNameModifyRequest;
import kr.co.fastcampus.yanabada.domain.member.dto.request.PasswordModifyRequest;
import kr.co.fastcampus.yanabada.domain.member.dto.request.PhoneNumberModifyRequest;
import kr.co.fastcampus.yanabada.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
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
        log.info("provider={}", principalDetails.provider());
        log.info("provider={}", principalDetails.provider().name());
        return "test";
    }

    @PutMapping("/password")
    public ResponseBody<Void> modifyPassword(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @RequestBody PasswordModifyRequest passwordRequest
    ) {
        memberService.modifyPassword(
            passwordRequest, principalDetails.email(), principalDetails.provider()
        );
        return ResponseBody.ok();
    }

    @PutMapping("/nickname")
    public ResponseBody<Void> modifyNickName(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @RequestBody NickNameModifyRequest nickNameRequest
    ) {
        memberService.modifyNickName(
            nickNameRequest, principalDetails.email(), principalDetails.provider()
        );
        return ResponseBody.ok();
    }

    @PutMapping("/phone-number")
    public ResponseBody<Void> modifyPhoneNumber(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @RequestBody PhoneNumberModifyRequest phoneNumberRequest
    ) {
        memberService.modifyPhoneNumber(
            phoneNumberRequest, principalDetails.email(), principalDetails.provider()
        );
        return ResponseBody.ok();
    }

    @PutMapping("/image")
    public ResponseBody<Void> modifyImage(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @RequestBody ImgUrlModifyRequest imgUrlRequest
    ) {
        memberService.modifyImageUrl(
            imgUrlRequest, principalDetails.email(), principalDetails.provider()
        );
        return ResponseBody.ok();
    }

}
