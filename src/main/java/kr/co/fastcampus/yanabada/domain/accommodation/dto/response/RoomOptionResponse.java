package kr.co.fastcampus.yanabada.domain.accommodation.dto.response;

import kr.co.fastcampus.yanabada.domain.accommodation.entity.RoomOption;
import lombok.Builder;

@Builder
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
        return RoomOptionResponse.builder()
            .canPark(roomOption.getCanPark())
            .isPartyRoom(roomOption.getIsPartyRoom())
            .canAccompanyPet(roomOption.getCanAccompanyPet())
            .isKidsRoom(roomOption.getIsKidsRoom())
            .isCityView(roomOption.getIsCityView())
            .isOceanView(roomOption.getIsOceanView())
            .hasPc(roomOption.getHasPc())
            .hasOtt(roomOption.getHasOtt())
            .hasBathtub(roomOption.getHasBathtub())
            .hasAmenity(roomOption.getHasAmenity())
            .hasBreakfast(roomOption.getHasBreakfast())
            .canCook(roomOption.getCanCook())
            .isNoKids(roomOption.getIsNoKids())
            .build();
    }
}
