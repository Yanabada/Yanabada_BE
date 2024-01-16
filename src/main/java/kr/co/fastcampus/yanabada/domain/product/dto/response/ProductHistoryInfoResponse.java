package kr.co.fastcampus.yanabada.domain.product.dto.response;

import java.time.LocalDate;
import kr.co.fastcampus.yanabada.domain.product.entity.Product;
import kr.co.fastcampus.yanabada.domain.product.entity.enums.ProductStatus;
import lombok.Builder;

@Builder
public record ProductHistoryInfoResponse(
    Long id,
    String image,
    String accommodationName,
    String roomName,
    LocalDate saleEnd,
    Integer sellingPrice,
    ProductStatus status
) {

    public static ProductHistoryInfoResponse from(Product product) {
        return ProductHistoryInfoResponse.builder()
            .id(product.getId())
            .image(product.getOrder().getRoom().getAccommodation().getImage())
            .accommodationName(product.getOrder().getRoom().getAccommodation().getName())
            .roomName(product.getOrder().getRoom().getName())
            .saleEnd(product.getSaleEndDate())
            .sellingPrice(product.getPrice())
            .status(product.getStatus())
            .build();
    }
}
