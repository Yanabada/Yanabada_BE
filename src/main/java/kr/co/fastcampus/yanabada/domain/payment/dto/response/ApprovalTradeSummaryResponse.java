package kr.co.fastcampus.yanabada.domain.payment.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import kr.co.fastcampus.yanabada.domain.payment.entity.Trade;
import kr.co.fastcampus.yanabada.domain.payment.entity.enums.TradeStatus;
import lombok.Builder;

@Builder
public record ApprovalTradeSummaryResponse(
    Long tradeId,
    String accommodationName,
    String accommodationImage,
    String buyerNickname,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime tradeRegisteredTime,
    String roomName,
    Integer sellingPrice,
    TradeStatus status
) {

    public static ApprovalTradeSummaryResponse from(Trade trade) {
        return ApprovalTradeSummaryResponse.builder()
            .tradeId(trade.getId())
            .accommodationName(
                trade.getProduct().getOrder().getRoom().getAccommodation().getName()
            )
            .accommodationImage(
                trade.getProduct().getOrder().getRoom().getAccommodation().getImage()
            )
            .buyerNickname(trade.getBuyer().getNickName())
            .tradeRegisteredTime(trade.getRegisteredDate())
            .roomName(trade.getProduct().getOrder().getRoom().getName())
            .sellingPrice(trade.getSellingPrice())
            .status(trade.getStatus())
            .build();
    }
}
