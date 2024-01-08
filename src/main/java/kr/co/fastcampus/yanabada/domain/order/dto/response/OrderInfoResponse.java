package kr.co.fastcampus.yanabada.domain.order.dto.response;

import java.time.LocalDate;
import java.util.Map;
import kr.co.fastcampus.yanabada.domain.order.entity.Order;
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

    public static OrderInfoResponse from(Order Order) {
        return OrderInfoResponse.builder()
            .orderId(Order.getId())
            .orderDate(Order.getCheckInDate())
            .accommodationName(Order.getRoom().getAccommodation().getName())
            .accommodationImage(Order.getRoom().getAccommodation().getImage())
            .roomName(Order.getRoom().getName())
            .checkInDate(Order.getCheckInDate())
            .checkOutDate(Order.getCheckOutDate())
            .price(Order.getPrice())
            .adultCount(Order.getAdultCount())
            .childCount(Order.getChildCount())
            .maxHeadCount(Order.getRoom().getMaxHeadCount())
            .reservationPersonName(Order.getReservationPersonName())
            .reservationPersonPhoneNumber(Order.getReservationPersonPhoneNumber())
            .userPersonName(Order.getUserPersonName())
            .userPersonPhoneNumber(Order.getUserPersonPhoneNumber())
            .roomOptions(null) // RoomOptions는 현재 구현되지 않았습니다. 화면 디자인 Fix 후 업데이트 예정.
            .totalPayment(Order.getPrice())
            .paymentMethod(Order.getPaymentType().name())
            .build();
    }
}
