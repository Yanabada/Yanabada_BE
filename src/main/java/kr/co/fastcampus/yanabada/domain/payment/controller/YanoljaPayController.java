package kr.co.fastcampus.yanabada.domain.payment.controller;

import jakarta.validation.Valid;
import kr.co.fastcampus.yanabada.common.response.ResponseBody;
import kr.co.fastcampus.yanabada.domain.payment.dto.request.YanoljaPayAmountRequest;
import kr.co.fastcampus.yanabada.domain.payment.dto.request.YanoljaPayHistorySearchRequest;
import kr.co.fastcampus.yanabada.domain.payment.dto.request.YanoljaPaySaveRequest;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.YanoljaPayHistoryIdResponse;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.YanoljaPayHistoryInfoResponse;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.YanoljaPayHistorySummaryPageResponse;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.YanoljaPayInfoResponse;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.YanoljaPaySummaryResponse;
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

    @GetMapping("/summary")
    public ResponseBody<YanoljaPaySummaryResponse> getYanoljaPaySummary() {
        return ResponseBody.ok(yanoljaPayService.getYanoljaPaySummary(2L));
    }

    @GetMapping
    public ResponseBody<YanoljaPayInfoResponse> getYanoljaPay() {
        return ResponseBody.ok(yanoljaPayService.getYanoljaPay(2L));
    }

    @PostMapping
    public ResponseBody<Void> saveYanoljaPay(
        @RequestBody @Valid YanoljaPaySaveRequest request
    ) {
        yanoljaPayService.saveYanoljaPay(2L, request);
        return ResponseBody.ok();
    }

    @PostMapping("/charge")
    public ResponseBody<YanoljaPayHistoryIdResponse> chargeYanoljaPay(
        @RequestBody @Valid YanoljaPayAmountRequest request
    ) {
        return ResponseBody.ok(
            yanoljaPayService.chargeYanoljaPay(2L, request)
        );
    }

    @PostMapping("/disburse")
    public ResponseBody<YanoljaPayHistoryIdResponse> disburseYanoljaPay(
        @RequestBody @Valid YanoljaPayAmountRequest request
    ) {
        return ResponseBody.ok(
            yanoljaPayService.disburseYanoljaPay(1L, request)
        );
    }

    @GetMapping("/histories")
    public ResponseBody<YanoljaPayHistorySummaryPageResponse> getYanoljaPayHistories(
        YanoljaPayHistorySearchRequest request
    ) {
        return ResponseBody.ok(
            yanoljaPayService.getYanoljaPayHistoriesBySearchRequest(
                2L,
                request
            )
        );
    }

    @GetMapping("/histories/{historyId}")
    public ResponseBody<YanoljaPayHistoryInfoResponse> getYanoljaPayHistory(
        @PathVariable("historyId") Long historyId
    ) {
        return ResponseBody.ok(
            yanoljaPayService.getYanoljaPayHistory(
                2L,
                historyId
            )
        );
    }
}

