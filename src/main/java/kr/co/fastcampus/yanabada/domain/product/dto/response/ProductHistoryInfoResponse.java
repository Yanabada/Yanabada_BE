package kr.co.fastcampus.yanabada.domain.product.dto.response;

import kr.co.fastcampus.yanabada.domain.product.entity.enums.ProductStatus;

import java.time.LocalDate;

public record ProductHistoryInfoResponse(
    Long id,
    String image,
    String accommodationName,
    String roomName,
    LocalDate saleEnd,
    Integer sellingPrice,
    ProductStatus status
) {
}
