package kr.co.fastcampus.yanabada.domain.payment.controller;

import kr.co.fastcampus.yanabada.common.response.ResponseBody;
import kr.co.fastcampus.yanabada.domain.payment.dto.request.TradeSaveRequest;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.TradeIdResponse;
import kr.co.fastcampus.yanabada.domain.payment.service.TradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
}
