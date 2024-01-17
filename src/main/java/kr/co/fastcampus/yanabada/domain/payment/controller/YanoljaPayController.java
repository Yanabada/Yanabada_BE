package kr.co.fastcampus.yanabada.domain.payment.controller;

import jakarta.validation.Valid;
import kr.co.fastcampus.yanabada.common.exception.MemberNotFoundException;
import kr.co.fastcampus.yanabada.common.exception.PasswordConfirmationException;
import kr.co.fastcampus.yanabada.common.exception.YanoljaPayNotFoundException;
import kr.co.fastcampus.yanabada.common.response.ResponseBody;
import kr.co.fastcampus.yanabada.domain.payment.dto.request.PayPasswordRequest;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.YanoljaPayHomeResponse;
import kr.co.fastcampus.yanabada.domain.payment.service.YanoljaPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class YanoljaPayController {

    private final YanoljaPayService yanoljaPayService;

    @GetMapping("/{memberId}")
    public ResponseBody<YanoljaPayHomeResponse> getYanoljaPay(@PathVariable Long memberId) {
        return ResponseBody.ok(yanoljaPayService.getYanoljaPay(memberId));
    }

    @PostMapping("/payPassword/{memberId}")
    public ResponseBody<ResponseEntity<?>> setPayPassword(
        @PathVariable("memberId") Long memberId,
        @RequestBody @Valid PayPasswordRequest payPasswordRequest
    ) {
        yanoljaPayService.setPayPassword(memberId, payPasswordRequest);
        return ResponseBody.ok(ResponseEntity.ok().build());
    }
}

