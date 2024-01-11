package kr.co.fastcampus.yanabada.domain.accommodation.dto.response;

import kr.co.fastcampus.yanabada.domain.accommodation.entity.RoomOption;
import lombok.Builder;

@Builder
public record RoomOptionInfoResponse(
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

    public static RoomOptionInfoResponse from(RoomOption option) {
        return RoomOptionInfoResponse.builder()
            .canPark(option.getCanPark())
            .isPartyRoom(option.getIsPartyRoom())
            .canAccompanyPet(option.getCanAccompanyPet())
            .isKidsRoom(option.getIsKidsRoom())
            .isCityView(option.getIsCityView())
            .isOceanView(option.getIsOceanView())
            .hasPc(option.getHasPc())
            .hasOtt(option.getHasOtt())
            .hasBathtub(option.getHasBathtub())
            .hasBreakfast(option.getHasBreakfast())
            .canCook(option.getCanCook())
            .isNoKids(option.getIsNoKids())
            .build();
    }
}
