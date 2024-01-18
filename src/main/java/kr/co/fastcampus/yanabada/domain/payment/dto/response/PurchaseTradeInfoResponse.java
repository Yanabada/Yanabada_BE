package kr.co.fastcampus.yanabada.domain.payment.dto.response;

import java.time.LocalDateTime;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.Accommodation;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.Room;
import kr.co.fastcampus.yanabada.domain.order.entity.enums.PaymentType;
import kr.co.fastcampus.yanabada.domain.payment.entity.Trade;
import kr.co.fastcampus.yanabada.domain.payment.entity.enums.TradeStatus;
import kr.co.fastcampus.yanabada.domain.product.entity.Product;
import lombok.Builder;

@Builder
public record PurchaseTradeInfoResponse(
    Long tradeId,
    TradeStatus status,
    Integer price,
    Integer sellingPrice,
    Integer fee,
    PaymentType paymentType,
    String accommodationName,
    String accommodationImage,
    String roomName,
    String tradeCode,
    String sellerName,
    LocalDateTime registeredDate
) {
    public static PurchaseTradeInfoResponse from(Trade trade) {
        Product product = trade.getProduct();
        Room room = product.getOrder().getRoom();
        Accommodation accommodation = room.getAccommodation();

        return PurchaseTradeInfoResponse.builder()
            .tradeId(trade.getId())
            .status(trade.getStatus())
            .price(trade.getPrice())
            .sellingPrice(trade.getSellingPrice())
            .fee(trade.getFee())
            .paymentType(trade.getPaymentType())
            .accommodationName(accommodation.getName())
            .accommodationImage(accommodation.getImage())
            .roomName(room.getName())
            .tradeCode(trade.getCode())
            .sellerName(trade.getSeller().getNickName())
            .registeredDate(trade.getRegisteredDate())
            .build();
    }
}
