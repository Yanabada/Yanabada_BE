package kr.co.fastcampus.yanabada.domain.accommodation.dto.request;

import java.time.LocalTime;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.Accommodation;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.Room;


public record RoomSaveRequest(
    Long accommodationId,
    String name,
    Integer price,
    LocalTime checkInTime,
    LocalTime checkOutTime,
    String description,
    Integer minHeadCount,
    Integer maxHeadCount,
    Double rating
) {

    public Room toEntity(Accommodation accommodation) {
        return Room.create(
            accommodation,
            name,
            price,
            checkInTime,
            checkOutTime,
            description,
            minHeadCount,
            maxHeadCount,
            rating
        );
    }
}
