package kr.co.fastcampus.yanabada.domain.order.dto.response;

import java.time.LocalDate;
import java.util.Map;
import kr.co.fastcampus.yanabada.domain.order.entity.RoomOrder;

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
    Map<String, Boolean> roomOptions,
    Integer totalPayment,
    String paymentMethod
) {
    public static OrderInfoResponse from(RoomOrder roomOrder) {
        return new OrderInfoResponse(
            roomOrder.getId(),
            roomOrder.getCheckInDate(),
            roomOrder.getRoom().getAccommodation().getName(),
            roomOrder.getRoom().getAccommodation().getImage(),
            roomOrder.getRoom().getName(),
            roomOrder.getCheckInDate(),
            roomOrder.getCheckOutDate(),
            roomOrder.getPrice(),
            roomOrder.getAdultCount(),
            roomOrder.getChildCount(),
            roomOrder.getRoom().getMaxHeadCount(),
            roomOrder.getReservationPersonName(),
            roomOrder.getReservationPersonPhoneNumber(),
            roomOrder.getUserPersonName(),
            roomOrder.getUserPersonPhoneNumber(),
            null, // RoomOptions는 현재 구현되지 않았습니다.
            roomOrder.getPrice(),
            roomOrder.getPaymentType().name()
        );
    }
}

