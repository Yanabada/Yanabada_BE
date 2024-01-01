package kr.co.fastcampus.Yanabada.domain.test.dto;

import kr.co.fastcampus.Yanabada.domain.test.entity.TestEntity;
import lombok.Builder;

@Builder
public record TestSaveRequest(
    String name,
    String email
) {

    public TestEntity toEntity() {
        return TestEntity.builder()
            .name(name)
            .email(email)
            .build();
    }
}
