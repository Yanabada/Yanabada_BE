package kr.co.fastcampus.yanabada.domain.payment.controller;

import kr.co.fastcampus.yanabada.common.response.ResponseBody;
import kr.co.fastcampus.yanabada.domain.payment.dto.request.TradeSaveRequest;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.ApprovalTradeInfoResponse;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.PurchaseTradeInfoResponse;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.TradeIdResponse;
import kr.co.fastcampus.yanabada.domain.payment.service.TradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/trades")
public class TradeController {

    private final TradeService tradeService;

    @PostMapping
    public ResponseBody<TradeIdResponse> addTrade(
        @RequestBody TradeSaveRequest request
    ) {
        return ResponseBody.ok(
            tradeService.saveTrade(2L, request)
        );
    }

    @PostMapping("/{tradeId}/approve")
    public ResponseBody<Void> approveTrade(
        @PathVariable("tradeId") Long tradeId
    ) {
        tradeService.approveTrade(1L, tradeId);
        return ResponseBody.ok();
    }

    @PostMapping("/{tradeId}/reject")
    public ResponseBody<Void> rejectTrade(
        @PathVariable("tradeId") Long tradeId
    ) {
        tradeService.rejectTrade(1L, tradeId);
        return ResponseBody.ok();
    }

    @PostMapping("/{tradeId}/cancel")
    public ResponseBody<Void> cancelTrade(
        @PathVariable("tradeId") Long tradeId
    ) {
        tradeService.cancelTrade(2L, tradeId);
        return ResponseBody.ok();
    }

    @GetMapping("/approvals/{tradeId}")
    public ResponseBody<ApprovalTradeInfoResponse> getApprovalTrade(
        @PathVariable("tradeId") Long tradeId
    ) {
        return ResponseBody.ok(
            tradeService.getApprovalTrade(1L, tradeId)
        );
    }

    @GetMapping("/purchases/{tradeId}")
    public ResponseBody<PurchaseTradeInfoResponse> getPurchaseTrade(
        @PathVariable("tradeId") Long tradeId
    ) {
        return ResponseBody.ok(
            tradeService.getPurchaseTrade(2L, tradeId)
        );
    }

    @DeleteMapping("/{tradeId}")
    public ResponseBody<Void> deleteTrade(
        @PathVariable("tradeId") Long tradeId
    ) {
        tradeService.deleteTrade(1L, tradeId);
        return ResponseBody.ok();
    }
}
