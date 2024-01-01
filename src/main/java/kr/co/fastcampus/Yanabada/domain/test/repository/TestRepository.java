package kr.co.fastcampus.Yanabada.domain.test.repository;

import kr.co.fastcampus.Yanabada.domain.test.entity.TestEntity;
import org.springframework.data.repository.CrudRepository;

public interface TestRepository extends CrudRepository<TestEntity, Long> {

}
