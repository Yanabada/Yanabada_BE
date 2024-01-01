package kr.co.fastcampus.yanabada.domain.test.repository;

import kr.co.fastcampus.yanabada.domain.test.entity.TestEntity;
import org.springframework.data.repository.CrudRepository;

public interface TestRepository extends CrudRepository<TestEntity, Long> {

}
