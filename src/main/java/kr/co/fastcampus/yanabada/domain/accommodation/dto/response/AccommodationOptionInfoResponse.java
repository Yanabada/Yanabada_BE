package kr.co.fastcampus.yanabada.domain.accommodation.dto.response;

import kr.co.fastcampus.yanabada.domain.accommodation.entity.AccommodationOption;
import lombok.Builder;

@Builder
public record AccommodationOptionInfoResponse(
    Boolean hasSauna,
    Boolean hasRoofTop,
    Boolean hasPool,
    Boolean hasGym,
    Boolean hasLoungeBar
) {

    public static AccommodationOptionInfoResponse from(AccommodationOption option) {
        return AccommodationOptionInfoResponse.builder()
            .hasSauna(option.getHasSauna())
            .hasRoofTop(option.getHasRooftop())
            .hasPool(option.getHasPool())
            .hasGym(option.getHasGym())
            .hasLoungeBar(option.getHasLoungeBar())
            .build();
    }
}
