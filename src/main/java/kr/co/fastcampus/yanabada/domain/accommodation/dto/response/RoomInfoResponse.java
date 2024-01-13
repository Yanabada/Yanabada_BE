package kr.co.fastcampus.yanabada.domain.accommodation.dto.response;

import java.time.LocalTime;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.Room;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.enums.RoomCancelPolicy;
import lombok.Builder;

@Builder
public record RoomInfoResponse(
    Long id,
    String name,
    Integer price,
    LocalTime checkInTime,
    LocalTime checkOutTime,
    String description,
    Integer minHeadCount,
    Integer maxHeadCount,
    Double rating,
    String image,
    RoomCancelPolicy cancelPolicy,
    RoomOptionInfoResponse option
) {

    public static RoomInfoResponse from(Room room) {
        return RoomInfoResponse.builder()
            .id(room.getId())
            .name(room.getName())
            .price(room.getPrice())
            .checkInTime(room.getCheckInTime())
            .checkOutTime(room.getCheckOutTime())
            .description(room.getDescription())
            .minHeadCount(room.getMinHeadCount())
            .maxHeadCount(room.getMaxHeadCount())
            .rating(room.getRating())
            .image(room.getImage())
            .cancelPolicy(room.getCancelPolicy())
            .option(RoomOptionInfoResponse.from(room.getRoomOption()))
            .build();
    }
}
