package kr.co.fastcampus.yanabada.domain.order.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.Accommodation;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.Room;
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
    @JsonFormat(pattern = "HH:mm")
    LocalTime checkInTime,
    @JsonFormat(pattern = "HH:mm")
    LocalTime checkOutTime,
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
        Room room = order.getRoom();
        Accommodation accommodation = room.getAccommodation();

        return OrderInfoResponse.builder()
            .orderId(order.getId())
            .code(order.getCode())
            .orderDate(order.getCheckInDate())
            .accommodationName(accommodation.getName())
            .accommodationImage(accommodation.getImage())
            .roomName(room.getName())
            .cancelPolicy(room.getCancelPolicy())
            .checkInDate(order.getCheckInDate())
            .checkOutDate(order.getCheckOutDate())
            .checkInTime(room.getCheckInTime())
            .checkOutTime(room.getCheckOutTime())
            .price(order.getPrice())
            .minHeadCount(room.getMinHeadCount())
            .maxHeadCount(room.getMaxHeadCount())
            .reservationPersonName(order.getReservationPersonName())
            .reservationPersonPhoneNumber(order.getReservationPersonPhoneNumber())
            .userPersonName(order.getUserPersonName())
            .userPersonPhoneNumber(order.getUserPersonPhoneNumber())
            .totalPayment(order.getPrice())
            .paymentMethod(order.getPaymentType().name())
            .build();
    }
}
