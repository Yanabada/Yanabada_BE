package kr.co.fastcampus.yanabada.domain.product.dto.response;

import java.time.LocalDate;
import kr.co.fastcampus.yanabada.domain.product.entity.enums.ProductStatus;

public record ProductSummaryResponse(
    Long id,
    String image,
    String accommodationName,
    String roomName,
    LocalDate checkIn,
    LocalDate checkOut,
    Integer min,
    Integer max,
    LocalDate saleEnd,
    Double rating,
    Integer salesPercentage,
    Boolean canNegotiate,
    Integer price,
    Integer sellingPrice,
    Integer purchasePrice,
    Double latitude,
    Double longitude,
    ProductStatus status
) {

}
