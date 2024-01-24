package kr.co.fastcampus.yanabada.domain.accommodation.dto.request;

import kr.co.fastcampus.yanabada.domain.accommodation.entity.Accommodation;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.AccommodationOption;

public record AccommodationOptionSaveRequest(
    Long accommodationId,
    Boolean hasSauna,
    Boolean hasRooftop,
    Boolean hasPool,
    Boolean hasGym,
    Boolean hasLoungeBar
) {

    public AccommodationOption toEntity(Accommodation accommodation) {
        return AccommodationOption.create(
            accommodation,
            hasSauna,
            hasRooftop,
            hasPool,
            hasGym,
            hasLoungeBar
        );
    }
}
