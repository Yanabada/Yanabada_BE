package kr.co.fastcampus.yanabada.domain.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

import kr.co.fastcampus.yanabada.domain.order.entity.Order;
import kr.co.fastcampus.yanabada.domain.product.entity.Product;
import kr.co.fastcampus.yanabada.domain.product.entity.enums.ProductStatus;

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

    public Product toEntity(
        Order order, LocalDateTime registeredDate
    ) {
        return Product.create(
            order,
            price,
            description,
            canNegotiate,
            saleEndDate,
            isAutoCancel,
            registeredDate,
            ProductStatus.ON_SALE
        );
    }
}
