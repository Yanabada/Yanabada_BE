package kr.co.fastcampus.yanabada.domain.order.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.Accommodation;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.Room;
import kr.co.fastcampus.yanabada.domain.order.entity.Order;
import lombok.Builder;

@Builder
public record OrderSummaryResponse(
    Long id,
    String code,
    String image,
    String accommodationName,
    String roomName,
    LocalDate checkInDate,
    LocalDate checkOutDate,
    @JsonFormat(pattern = "HH:mm")
    LocalTime checkInTime,
    @JsonFormat(pattern = "HH:mm")
    LocalTime checkOutTime
) {

    public static OrderSummaryResponse from(Order order) {
        Room room = order.getRoom();
        Accommodation accommodation = room.getAccommodation();

        return OrderSummaryResponse.builder()
            .id(order.getId())
            .code(order.getCode())
            .image(accommodation.getImage())
            .accommodationName(accommodation.getName())
            .roomName(room.getName())
            .checkInDate(order.getCheckInDate())
            .checkOutDate(order.getCheckOutDate())
            .checkInTime(room.getCheckInTime())
            .checkOutTime(room.getCheckOutTime())
            .build();
    }
}
