package kr.co.fastcampus.yanabada.domain.order.dto.response;

import java.time.LocalDate;
import java.util.Map;
import kr.co.fastcampus.yanabada.domain.order.entity.RoomOrder;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderInfoResponse {

    private final Long orderId;
    private final LocalDate orderDate;
    private final String accommodationName;
    private final String accommodationImage;
    private final String roomName;
    private final LocalDate checkInDate;
    private final LocalDate checkOutDate;
    private final Integer price;
    private final Integer adultCount;
    private final Integer childCount;
    private final Integer maxHeadCount;
    private final String reservationPersonName;
    private final String reservationPersonPhoneNumber;
    private final String userPersonName;
    private final String userPersonPhoneNumber;
    private final Map<String, Boolean> roomOptions;
    private final Integer totalPayment;
    private final String paymentMethod;

    public static OrderInfoResponse from(RoomOrder roomOrder) {
        return OrderInfoResponse.builder()
            .orderId(roomOrder.getId())
            .orderDate(roomOrder.getCheckInDate())
            .accommodationName(roomOrder.getRoom().getAccommodation().getName())
            .accommodationImage(roomOrder.getRoom().getAccommodation().getImage())
            .roomName(roomOrder.getRoom().getName())
            .checkInDate(roomOrder.getCheckInDate())
            .checkOutDate(roomOrder.getCheckOutDate())
            .price(roomOrder.getPrice())
            .adultCount(roomOrder.getAdultCount())
            .childCount(roomOrder.getChildCount())
            .maxHeadCount(roomOrder.getRoom().getMaxHeadCount())
            .reservationPersonName(roomOrder.getReservationPersonName())
            .reservationPersonPhoneNumber(roomOrder.getReservationPersonPhoneNumber())
            .userPersonName(roomOrder.getUserPersonName())
            .userPersonPhoneNumber(roomOrder.getUserPersonPhoneNumber())
            .roomOptions(null) // RoomOptions는 현재 구현되지 않았습니다. 화면 디자인 Fix 후 업데이트 예정.
            .totalPayment(roomOrder.getPrice())
            .paymentMethod(roomOrder.getPaymentType().name())
            .build();
    }
}
