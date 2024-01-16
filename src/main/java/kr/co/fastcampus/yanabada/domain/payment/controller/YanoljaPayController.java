package kr.co.fastcampus.yanabada.domain.payment.controller;

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
    public ResponseEntity<YanoljaPayHomeResponse> getYanoljaPay(@PathVariable Long memberId) {
        Member member = memberRepository.findById(memberId).orElse(null);

        if (member != null) {
            YanoljaPayHomeResponse response = yanoljaPayService.getYanoljaPayData(member);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
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

