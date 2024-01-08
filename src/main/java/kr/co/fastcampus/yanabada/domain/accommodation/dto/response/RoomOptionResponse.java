package kr.co.fastcampus.yanabada.domain.accommodation.dto.response;

import kr.co.fastcampus.yanabada.domain.accommodation.entity.RoomOption;

public record RoomOptionResponse(
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

    public static RoomOptionResponse from(RoomOption roomOption) {
        return new RoomOptionResponse(
            roomOption.getCanPark(),
            roomOption.getIsPartyRoom(),
            roomOption.getCanAccompanyPet(),
            roomOption.getIsKidsRoom(),
            roomOption.getIsCityView(),
            roomOption.getIsOceanView(),
            roomOption.getHasPc(),
            roomOption.getHasOtt(),
            roomOption.getHasBathtub(),
            roomOption.getHasAmenity(),
            roomOption.getHasBreakfast(),
            roomOption.getCanCook(),
            roomOption.getIsNoKids()
        );
    }
}
