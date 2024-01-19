package kr.co.fastcampus.yanabada.domain.accommodation.dto.request;

import java.time.LocalTime;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.Accommodation;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.Room;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.enums.RoomCancelPolicy;


public record RoomSaveRequest(
    Long accommodationId,
    String name,
    Integer price,
    LocalTime checkInTime,
    LocalTime checkOutTime,
    Integer minHeadCount,
    Integer maxHeadCount,
    Double rating,
    String image,
    RoomCancelPolicy cancelPolicy
) {

    public Room toEntity(Accommodation accommodation) {
        return Room.create(
            accommodation,
            name,
            price,
            checkInTime,
            checkOutTime,
            minHeadCount,
            maxHeadCount,
            rating,
            image,
            cancelPolicy
        );
    }
}
