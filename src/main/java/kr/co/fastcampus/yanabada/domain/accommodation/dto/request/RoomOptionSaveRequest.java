package kr.co.fastcampus.yanabada.domain.accommodation.dto.request;

import kr.co.fastcampus.yanabada.domain.accommodation.entity.Room;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.RoomOption;

public record RoomOptionSaveRequest(
    Long roomId,
    Boolean canPark,
    Boolean isPartyRoom,
    Boolean canAccompanyPet,
    Boolean isKidsRoom,
    Boolean isCityView,
    Boolean isOceanView,
    Boolean hasPc,
    Boolean hasOtt,
    Boolean hasBathtub,
    Boolean hasAmenity,
    Boolean hasBreakfast,
    Boolean canCook,
    Boolean isNoKids
) {

    public RoomOption toEntity(Room room) {
        return RoomOption.create(
            room,
            canPark,
            isPartyRoom,
            canAccompanyPet,
            isKidsRoom,
            isCityView,
            isOceanView,
            hasPc,
            hasOtt,
            hasBathtub,
            hasAmenity,
            hasBreakfast,
            canCook,
            isNoKids
        );
    }
}
