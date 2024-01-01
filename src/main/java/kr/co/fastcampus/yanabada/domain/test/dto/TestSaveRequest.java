package kr.co.fastcampus.yanabada.domain.test.dto;

import kr.co.fastcampus.yanabada.domain.test.entity.TestEntity;

public record TestSaveRequest(
    String name,
    String email
) {

    public TestEntity toEntity() {
        return TestEntity.create(name, email);
    }
}
