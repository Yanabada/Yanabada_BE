package kr.co.fastcampus.yanabada.domain.payment.controller;

import jakarta.validation.Valid;
import kr.co.fastcampus.yanabada.common.response.ResponseBody;
import kr.co.fastcampus.yanabada.common.security.PrincipalDetails;
import kr.co.fastcampus.yanabada.domain.payment.dto.request.YanoljaPayAmountRequest;
import kr.co.fastcampus.yanabada.domain.payment.dto.request.YanoljaPayHistorySearchRequest;
import kr.co.fastcampus.yanabada.domain.payment.dto.request.YanoljaPaySaveRequest;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.AdminPaymentInfoResponse;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.YanoljaPayHistoryIdResponse;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.YanoljaPayHistoryInfoResponse;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.YanoljaPayHistorySummaryPageResponse;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.YanoljaPayIdResponse;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.YanoljaPayInfoResponse;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.YanoljaPaySummaryResponse;
import kr.co.fastcampus.yanabada.domain.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/summary")
    public ResponseBody<YanoljaPaySummaryResponse> getYanoljaPaySummary(
        @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        return ResponseBody.ok(paymentService.getYanoljaPaySummary(principalDetails.id()));
    }

    @GetMapping
    public ResponseBody<YanoljaPayInfoResponse> getYanoljaPay(
        @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        return ResponseBody.ok(paymentService.getYanoljaPay(principalDetails.id()));
    }

    @PostMapping
    public ResponseBody<YanoljaPayIdResponse> saveYanoljaPay(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @RequestBody @Valid YanoljaPaySaveRequest request
    ) {
        return ResponseBody.ok(
            paymentService.saveYanoljaPay(principalDetails.id(), request)
        );
    }

    @PostMapping("/charge")
    public ResponseBody<YanoljaPayHistoryIdResponse> chargeYanoljaPay(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @RequestBody @Valid YanoljaPayAmountRequest request
    ) {
        return ResponseBody.ok(
            paymentService.chargeYanoljaPay(principalDetails.id(), request)
        );
    }

    @PostMapping("/disburse")
    public ResponseBody<YanoljaPayHistoryIdResponse> disburseYanoljaPay(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @RequestBody @Valid YanoljaPayAmountRequest request
    ) {
        return ResponseBody.ok(
            paymentService.disburseYanoljaPay(principalDetails.id(), request)
        );
    }

    @GetMapping("/histories")
    public ResponseBody<YanoljaPayHistorySummaryPageResponse> getYanoljaPayHistories(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        YanoljaPayHistorySearchRequest request
    ) {
        return ResponseBody.ok(
            paymentService.getYanoljaPayHistoriesBySearchRequest(
                principalDetails.id(),
                request
            )
        );
    }

    @GetMapping("/histories/{historyId}")
    public ResponseBody<YanoljaPayHistoryInfoResponse> getYanoljaPayHistory(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable("historyId") Long historyId
    ) {
        return ResponseBody.ok(
            paymentService.getYanoljaPayHistory(
                principalDetails.id(),
                historyId
            )
        );
    }

    @GetMapping("/admin")
    public ResponseBody<AdminPaymentInfoResponse> getAdminPayment() {
        return ResponseBody.ok(
            paymentService.getAdminPayment()
        );
    }
}

