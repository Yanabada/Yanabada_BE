package kr.co.fastcampus.yanabada.domain.order.dto.response;

import java.time.LocalDate;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.enums.RoomCancelPolicy;
import kr.co.fastcampus.yanabada.domain.order.entity.Order;
import lombok.Builder;

@Builder
public record OrderInfoResponse(
    Long orderId,
    String code,
    LocalDate orderDate,
    String accommodationName,
    String accommodationImage,
    String roomName,
    RoomCancelPolicy cancelPolicy,
    LocalDate checkInDate,
    LocalDate checkOutDate,
    Integer price,
    Integer minHeadCount,
    Integer maxHeadCount,
    String reservationPersonName,
    String reservationPersonPhoneNumber,
    String userPersonName,
    String userPersonPhoneNumber,
    Integer totalPayment,
    String paymentMethod
) {

    public static OrderInfoResponse from(Order order) {
        return OrderInfoResponse.builder()
            .orderId(order.getId())
            .code(order.getCode())
            .orderDate(order.getCheckInDate())
            .accommodationName(order.getRoom().getAccommodation().getName())
            .accommodationImage(order.getRoom().getAccommodation().getImage())
            .roomName(order.getRoom().getName())
            .cancelPolicy(order.getRoom().getCancelPolicy())
            .checkInDate(order.getCheckInDate())
            .checkOutDate(order.getCheckOutDate())
            .price(order.getPrice())
            .minHeadCount(order.getRoom().getMinHeadCount())
            .maxHeadCount(order.getRoom().getMaxHeadCount())
            .reservationPersonName(order.getReservationPersonName())
            .reservationPersonPhoneNumber(order.getReservationPersonPhoneNumber())
            .userPersonName(order.getUserPersonName())
            .userPersonPhoneNumber(order.getUserPersonPhoneNumber())
            .totalPayment(order.getPrice())
            .paymentMethod(order.getPaymentType().name())
            .build();
    }
}
