package kr.co.fastcampus.yanabada.domain.payment.dto.response;

import kr.co.fastcampus.yanabada.domain.payment.entity.Trade;
import lombok.Builder;

@Builder
public record TradeIdResponse(
    Long tradeId
) {
    public static TradeIdResponse from(Trade trade) {
        return TradeIdResponse.builder()
            .tradeId(trade.getId())
            .build();
    }
}
