package kr.co.fastcampus.yanabada.domain.trade.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import kr.co.fastcampus.yanabada.domain.trade.entity.Trade;
import kr.co.fastcampus.yanabada.domain.trade.entity.enums.TradeStatus;
import lombok.Builder;


@Builder
public record PurchaseTradeSummaryResponse(
    Long tradeId,
    Long productId,
    String accommodationName,
    String accommodationImage,
    String sellerNickname,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime tradeRegisteredTime,
    LocalDate saleEndDate,
    String roomName,
    Integer price,
    TradeStatus status
) {

    public static PurchaseTradeSummaryResponse from(Trade trade) {
        return PurchaseTradeSummaryResponse.builder()
            .tradeId(trade.getId())
            .productId(trade.getProduct().getId())
            .accommodationName(
                trade.getProduct().getOrder().getRoom().getAccommodation().getName()
            )
            .accommodationImage(
                trade.getProduct().getOrder().getRoom().getAccommodation().getImage()
            )
            .sellerNickname(trade.getSeller().getNickName())
            .tradeRegisteredTime(trade.getRegisteredDate())
            .saleEndDate(trade.getProduct().getSaleEndDate())
            .roomName(trade.getProduct().getOrder().getRoom().getName())
            .price(trade.getSellingPrice() + trade.getFee())
            .status(trade.getStatus())
            .build();
    }
}
