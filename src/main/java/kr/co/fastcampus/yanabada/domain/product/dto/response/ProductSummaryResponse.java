package kr.co.fastcampus.yanabada.domain.product.dto.response;

import java.time.LocalDate;
import kr.co.fastcampus.yanabada.common.utils.SalesPercentageCalculator;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.Accommodation;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.Room;
import kr.co.fastcampus.yanabada.domain.order.entity.Order;
import kr.co.fastcampus.yanabada.domain.product.entity.Product;
import kr.co.fastcampus.yanabada.domain.product.entity.enums.ProductStatus;
import lombok.Builder;

@Builder
public record ProductSummaryResponse(
    Long id,
    String image,
    String accommodationName,
    String roomName,
    String address,
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

    public static ProductSummaryResponse from(Product product) {
        Order order = product.getOrder();
        Room room = order.getRoom();
        Accommodation accommodation = room.getAccommodation();

        return ProductSummaryResponse.builder()
            .id(product.getId())
            .image(accommodation.getImage())
            .accommodationName(accommodation.getName())
            .roomName(room.getName())
            .address(accommodation.getAddress())
            .checkIn(order.getCheckInDate())
            .checkOut(order.getCheckOutDate())
            .min(room.getMinHeadCount())
            .max(room.getMaxHeadCount())
            .saleEnd(product.getSaleEndDate())
            .rating(room.getRating())
            .salesPercentage(
                SalesPercentageCalculator.calculate(room.getPrice(), product.getPrice())
            )
            .canNegotiate(product.getCanNegotiate())
            .price(room.getPrice())
            .sellingPrice(product.getPrice())
            .purchasePrice(order.getPrice())
            .latitude(accommodation.getLatitude())
            .longitude(accommodation.getLongitude())
            .status(product.getStatus())
            .build();
    }
}
