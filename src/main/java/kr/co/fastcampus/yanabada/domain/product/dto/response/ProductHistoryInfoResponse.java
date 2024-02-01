package kr.co.fastcampus.yanabada.domain.product.dto.response;

import java.time.LocalDate;
import kr.co.fastcampus.yanabada.domain.product.entity.Product;
import kr.co.fastcampus.yanabada.domain.product.entity.enums.ProductStatus;
import lombok.Builder;

@Builder
public record ProductHistoryInfoResponse(
    Long productId,
    Long tradeId,
    String image,
    String accommodationName,
    String roomName,
    LocalDate saleEnd,
    Integer sellingPrice,
    ProductStatus status
) {

    public static ProductHistoryInfoResponse from(Long tradeId, Product product) {
        return ProductHistoryInfoResponse.builder()
            .productId(product.getId())
            .tradeId(tradeId)
            .image(product.getOrder().getRoom().getAccommodation().getImage())
            .accommodationName(product.getOrder().getRoom().getAccommodation().getName())
            .roomName(product.getOrder().getRoom().getName())
            .saleEnd(product.getSaleEndDate())
            .sellingPrice(product.getPrice())
            .status(product.getStatus())
            .build();
    }
}
