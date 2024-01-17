package kr.co.fastcampus.yanabada.domain.payment.controller;

import jakarta.validation.Valid;
import kr.co.fastcampus.yanabada.common.response.ResponseBody;
import kr.co.fastcampus.yanabada.domain.payment.dto.request.PayPasswordSaveRequest;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.YanoljaPayHomeResponse;
import kr.co.fastcampus.yanabada.domain.payment.service.YanoljaPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/pay-password/{memberId}")
    public ResponseBody<Void> setPayPassword(
        @PathVariable("memberId") Long memberId,
        @RequestBody @Valid PayPasswordSaveRequest payPasswordSaveRequest
    ) {
        yanoljaPayService.setPayPassword(memberId, payPasswordSaveRequest);
        return ResponseBody.ok();
    }
}

