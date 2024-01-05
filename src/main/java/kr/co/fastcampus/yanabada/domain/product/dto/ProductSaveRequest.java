package kr.co.fastcampus.yanabada.domain.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record ProductSaveRequest(

    @NotNull
    Long orderId,

    @NotNull
    Integer price,

    @NotBlank
    String description,

    @NotNull
    Boolean canNegotiate,

    @NotNull
    LocalDate saleEndDate,

    @NotNull
    Boolean isAutoCancel
) {

}
