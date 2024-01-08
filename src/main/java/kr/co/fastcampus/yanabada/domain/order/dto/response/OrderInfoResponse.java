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

    public static OrderInfoResponse from(Order order) {
        return OrderInfoResponse.builder()
            .orderId(order.getId())
            .orderDate(order.getCheckInDate())
            .accommodationName(order.getRoom().getAccommodation().getName())
            .accommodationImage(order.getRoom().getAccommodation().getImage())
            .roomName(order.getRoom().getName())
            .checkInDate(order.getCheckInDate())
            .checkOutDate(order.getCheckOutDate())
            .price(order.getPrice())
            .adultCount(order.getAdultCount())
            .childCount(order.getChildCount())
            .maxHeadCount(order.getRoom().getMaxHeadCount())
            .reservationPersonName(order.getReservationPersonName())
            .reservationPersonPhoneNumber(order.getReservationPersonPhoneNumber())
            .userPersonName(order.getUserPersonName())
            .userPersonPhoneNumber(order.getUserPersonPhoneNumber())
            .roomOptions(null) // RoomOptions는 현재 구현되지 않았습니다. 화면 디자인 Fix 후 업데이트 예정.
            .totalPayment(order.getPrice())
            .paymentMethod(order.getPaymentType().name())
            .build();
    }
}
