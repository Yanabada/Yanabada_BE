package kr.co.fastcampus.yanabada.domain.test.dto;

import kr.co.fastcampus.yanabada.domain.test.entity.TestEntity;
import lombok.Builder;

@Builder
public record TestInfoResponse(
    Long id,
    String name,
    String email
) {

    public static TestInfoResponse from(TestEntity testEntity) {
        return TestInfoResponse.builder()
            .id(testEntity.getId())
            .name(testEntity.getName())
            .email(testEntity.getEmail())
            .build();
    }
}
