package kr.co.fastcampus.yanabada.domain.order.dto.response;

import java.time.LocalDate;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationDetailsDto {

    private final Long reservationId;
    private final LocalDate reservationDate;
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

}

