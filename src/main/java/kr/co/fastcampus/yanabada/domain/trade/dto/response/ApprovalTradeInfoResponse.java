package kr.co.fastcampus.yanabada.domain.trade.dto.response;

import java.time.LocalDateTime;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.Accommodation;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.Room;
import kr.co.fastcampus.yanabada.domain.trade.entity.Trade;
import kr.co.fastcampus.yanabada.domain.trade.entity.enums.TradeStatus;
import kr.co.fastcampus.yanabada.domain.product.entity.Product;
import lombok.Builder;

@Builder
public record ApprovalTradeInfoResponse(
    Long tradeId,
    TradeStatus status,
    Integer price,
    Integer sellingPrice,
    String accommodationName,
    String accommodationImage,
    String roomName,
    String tradeCode,
    String buyerName,
    LocalDateTime registeredDate
) {
    public static ApprovalTradeInfoResponse from(Trade trade) {
        Product product = trade.getProduct();
        Room room = product.getOrder().getRoom();
        Accommodation accommodation = room.getAccommodation();

        return ApprovalTradeInfoResponse.builder()
            .tradeId(trade.getId())
            .status(trade.getStatus())
            .price(trade.getPrice())
            .sellingPrice(trade.getSellingPrice())
            .accommodationName(accommodation.getName())
            .accommodationImage(accommodation.getImage())
            .roomName(room.getName())
            .tradeCode(trade.getCode())
            .buyerName(trade.getBuyer().getNickName())
            .registeredDate(trade.getRegisteredDate())
            .build();
    }
}
