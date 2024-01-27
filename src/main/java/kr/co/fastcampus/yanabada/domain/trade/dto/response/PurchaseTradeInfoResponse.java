package kr.co.fastcampus.yanabada.domain.trade.dto.response;

import java.time.LocalDateTime;
import kr.co.fastcampus.yanabada.domain.order.entity.enums.PaymentType;
import kr.co.fastcampus.yanabada.domain.trade.entity.Trade;
import kr.co.fastcampus.yanabada.domain.trade.entity.enums.TradeStatus;
import kr.co.fastcampus.yanabada.domain.product.dto.response.ProductSummaryResponse;
import kr.co.fastcampus.yanabada.domain.product.entity.Product;
import lombok.Builder;

@Builder
public record PurchaseTradeInfoResponse(
    Long tradeId,
    TradeStatus status,
    Integer price,
    Integer sellingPrice,
    Integer fee,
    Integer point,
    PaymentType paymentType,
    ProductSummaryResponse product,
    String tradeCode,
    String sellerName,
    LocalDateTime registeredDate
) {
    public static PurchaseTradeInfoResponse from(Trade trade) {
        Product product = trade.getProduct();

        return PurchaseTradeInfoResponse.builder()
            .tradeId(trade.getId())
            .status(trade.getStatus())
            .price(trade.getPrice())
            .sellingPrice(trade.getSellingPrice())
            .fee(trade.getFee())
            .point(trade.getPoint())
            .paymentType(trade.getPaymentType())
            .product(ProductSummaryResponse.from(product))
            .tradeCode(trade.getCode())
            .sellerName(trade.getSeller().getNickName())
            .registeredDate(trade.getRegisteredDate())
            .build();
    }
}
