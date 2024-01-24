package kr.co.fastcampus.yanabada.domain.payment.controller;

import kr.co.fastcampus.yanabada.common.response.ResponseBody;
import kr.co.fastcampus.yanabada.common.security.PrincipalDetails;
import kr.co.fastcampus.yanabada.domain.payment.dto.request.TradeSaveRequest;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.ApprovalTradeInfoResponse;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.ApprovalTradePageResponse;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.PurchaseTradeInfoResponse;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.PurchaseTradePageResponse;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.TradeIdResponse;
import kr.co.fastcampus.yanabada.domain.payment.entity.enums.TradeStatus;
import kr.co.fastcampus.yanabada.domain.payment.service.TradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/trades")
public class TradeController {

    private final TradeService tradeService;

    @PostMapping
    public ResponseBody<TradeIdResponse> addTrade(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @RequestBody TradeSaveRequest request
    ) {
        return ResponseBody.ok(
            tradeService.saveTrade(principalDetails.id(), request)
        );
    }

    @PostMapping("/{tradeId}/approve")
    public ResponseBody<TradeIdResponse> approveTrade(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable("tradeId") Long tradeId
    ) {
        return ResponseBody.ok(
            tradeService.approveTrade(principalDetails.id(), tradeId)
        );
    }

    @PostMapping("/{tradeId}/reject")
    public ResponseBody<TradeIdResponse> rejectTrade(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable("tradeId") Long tradeId
    ) {
        return ResponseBody.ok(
            tradeService.rejectTrade(principalDetails.id(), tradeId)
        );
    }

    @PostMapping("/{tradeId}/cancel")
    public ResponseBody<TradeIdResponse> cancelTrade(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable("tradeId") Long tradeId
    ) {
        return ResponseBody.ok(
            tradeService.cancelTrade(principalDetails.id(), tradeId)
        );
    }

    @GetMapping("/approvals/{tradeId}")
    public ResponseBody<ApprovalTradeInfoResponse> getApprovalTrade(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable("tradeId") Long tradeId
    ) {
        return ResponseBody.ok(
            tradeService.getApprovalTrade(principalDetails.id(), tradeId)
        );
    }

    @GetMapping("/approvals")
    public ResponseBody<ApprovalTradePageResponse> getApprovalTrades(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @RequestParam(name = "status", required = false) TradeStatus status,
        @PageableDefault(sort = "registeredDate", direction = Sort.Direction.DESC)
        Pageable pageable
    ) {
        return ResponseBody.ok(
            tradeService.getApprovalTrades(principalDetails.id(), status, pageable)
        );
    }

    @GetMapping("/purchases")
    public ResponseBody<PurchaseTradePageResponse> getPurchaseTrades(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @RequestParam(name = "status", required = false) TradeStatus status,
        @PageableDefault(sort = "registeredDate", direction = Sort.Direction.DESC)
        Pageable pageable
    ) {
        return ResponseBody.ok(
            tradeService.getPurchaseTrades(principalDetails.id(), status, pageable)
        );
    }

    @GetMapping("/purchases/{tradeId}")
    public ResponseBody<PurchaseTradeInfoResponse> getPurchaseTrade(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable("tradeId") Long tradeId
    ) {
        return ResponseBody.ok(
            tradeService.getPurchaseTrade(principalDetails.id(), tradeId)
        );
    }

    @DeleteMapping("/{tradeId}")
    public ResponseBody<TradeIdResponse> deleteTrade(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable("tradeId") Long tradeId
    ) {
        return ResponseBody.ok(
            tradeService.deleteTrade(principalDetails.id(), tradeId)
        );
    }
}
