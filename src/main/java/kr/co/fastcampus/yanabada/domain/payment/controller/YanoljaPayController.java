package kr.co.fastcampus.yanabada.domain.payment.controller;

import kr.co.fastcampus.yanabada.common.exception.YanoljaPayNotFoundException;
import kr.co.fastcampus.yanabada.common.response.ResponseBody;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.member.repository.MemberRepository;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.YanoljaPayHomeResponse;
import kr.co.fastcampus.yanabada.domain.payment.service.YanoljaPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/yanoljapay")
@RequiredArgsConstructor
public class YanoljaPayController {

    private final YanoljaPayService yanoljaPayService;
    private final MemberRepository memberRepository;

    @GetMapping("/{memberId}")
    public ResponseEntity<ResponseBody<YanoljaPayHomeResponse>> getYanoljaPay(
        @PathVariable Long memberId) {
        try {
            YanoljaPayHomeResponse response = yanoljaPayService
                .getYanoljaPayDataByMemberId(memberId);
            return ResponseEntity.ok(ResponseBody.ok(response));
        } catch (YanoljaPayNotFoundException e) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ResponseBody.fail(e.getMessage(), null));
        }
    }
}

//@RestController
//@RequestMapping("/yanoljapay")
//@RequiredArgsConstructor
//public class YanoljaPayController {
//
//    private final YanoljaPayService yanoljaPayService;
//    private final MemberRepository memberRepository;
//
//    @GetMapping("/{memberId}")
//    public ResponseEntity<YanoljaPayHomeResponse> getYanoljaPay(@PathVariable Long memberId) {
//        YanoljaPayHomeResponse response = yanoljaPayService.getYanoljaPayData(memberId);
//        return ResponseEntity.ok(response);
//    }
//}

