package kr.co.fastcampus.yanabada.domain.accommodation.dto.response;

import kr.co.fastcampus.yanabada.domain.accommodation.entity.Accommodation;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.enums.Category;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.enums.Region;
import lombok.Builder;

@Builder
public record AccommodationInfoResponse(
    Long id,
    String name,
    String address,
    Double latitude,
    Double longitude,
    Region region,
    String phoneNumber,
    String description,
    Category category,
    String image,
    AccommodationOptionInfoResponse option
) {

    public static AccommodationInfoResponse from(Accommodation accommodation) {
        return AccommodationInfoResponse.builder()
            .id(accommodation.getId())
            .name(accommodation.getName())
            .address(accommodation.getAddress())
            .latitude(accommodation.getLatitude())
            .longitude(accommodation.getLongitude())
            .region(accommodation.getRegion())
            .phoneNumber(accommodation.getPhoneNumber())
            .description(accommodation.getDescription())
            .category(accommodation.getCategory())
            .image(accommodation.getImage())
            .option(AccommodationOptionInfoResponse.from(accommodation.getAccommodationOption()))
            .build();
    }
}
