package kr.co.fastcampus.yanabada.domain.order.dto.response;

import java.time.LocalDate;
import kr.co.fastcampus.yanabada.domain.accommodation.dto.response.RoomOptionResponse;
import kr.co.fastcampus.yanabada.domain.order.entity.Order;

public record OrderInfoResponse(
    Long orderId,
    LocalDate orderDate,
    String accommodationName,
    String accommodationImage,
    String roomName,
    LocalDate checkInDate,
    LocalDate checkOutDate,
    Integer price,
    Integer adultCount,
    Integer childCount,
    Integer maxHeadCount,
    String reservationPersonName,
    String reservationPersonPhoneNumber,
    String userPersonName,
    String userPersonPhoneNumber,
    RoomOptionResponse roomOptions,
    Integer totalPayment,
    String paymentMethod
) {

    public static OrderInfoResponse from(Order order) {
        RoomOptionResponse roomOptionResponse = RoomOptionResponse.from(
            order.getRoom().getRoomOption());

        return new OrderInfoResponse(
            order.getId(),
            order.getCheckInDate(),
            order.getRoom().getAccommodation().getName(),
            order.getRoom().getAccommodation().getImage(),
            order.getRoom().getName(),
            order.getCheckInDate(),
            order.getCheckOutDate(),
            order.getPrice(),
            order.getAdultCount(),
            order.getChildCount(),
            order.getRoom().getMaxHeadCount(),
            order.getReservationPersonName(),
            order.getReservationPersonPhoneNumber(),
            order.getUserPersonName(),
            order.getUserPersonPhoneNumber(),
            roomOptionResponse,
            order.getPrice(),
            order.getPaymentType().name()
        );
    }
}
