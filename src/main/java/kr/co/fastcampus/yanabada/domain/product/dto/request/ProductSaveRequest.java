package kr.co.fastcampus.yanabada.domain.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalDateTime;
import kr.co.fastcampus.yanabada.domain.order.entity.Order;
import kr.co.fastcampus.yanabada.domain.product.entity.Product;
import kr.co.fastcampus.yanabada.domain.product.entity.enums.ProductStatus;

public record ProductSaveRequest(

    @NotNull(message = "예약 ID는 필수로 입력하셔야 합니다.")
    @Positive(message = "예약 ID는 양수이어야 합니다.")
    Long orderId,

    @NotNull(message = "가격은 필수로 입력하셔야 합니다.")
    @Positive(message = "가격은 양수이어야 합니다.")
    Integer price,

    @NotBlank(message = "판매자 한마디는 필수로 입력하셔야 합니다.")
    String description,

    @NotNull(message = "가격제안 여부는 필수로 입력하셔야 합니다.")
    Boolean canNegotiate,

    @NotNull(message = "판매 중단 날짜는 필수로 입력하셔야 합니다.")
    LocalDate saleEndDate,

    @NotNull(message = "자동 예약 취소 여부는 필수로 입력하셔야 합니다.")
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
