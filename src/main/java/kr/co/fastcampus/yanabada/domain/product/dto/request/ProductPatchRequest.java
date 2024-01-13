package kr.co.fastcampus.yanabada.domain.product.dto.request;

import java.time.LocalDate;

public record ProductPatchRequest(
    Integer price,
    String description,
    Boolean canNegotiate,
    LocalDate saleEndDate,
    Boolean isAutoCancel
) {

}
