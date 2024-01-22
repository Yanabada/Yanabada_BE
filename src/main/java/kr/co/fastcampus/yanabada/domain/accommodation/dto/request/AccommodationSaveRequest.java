package kr.co.fastcampus.yanabada.domain.accommodation.dto.request;

import kr.co.fastcampus.yanabada.domain.accommodation.entity.Accommodation;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.enums.Category;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.enums.Region;

public record AccommodationSaveRequest(
    String name,
    String address,
    Double longitude,
    Double latitude,
    Region region,
    String phoneNumber,
    String description,
    Category category,
    String image
) {

    public Accommodation toEntity(String s3EndPoint) {
        return Accommodation.create(
            name,
            address,
            longitude,
            latitude,
            region,
            phoneNumber,
            description,
            category,
            s3EndPoint + image
        );
    }
}
