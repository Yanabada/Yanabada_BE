package kr.co.fastcampus.Yanabada.domain.test.service;

import kr.co.fastcampus.Yanabada.domain.test.dto.TestInfoResponse;
import kr.co.fastcampus.Yanabada.domain.test.dto.TestSaveRequest;
import kr.co.fastcampus.Yanabada.domain.test.entity.TestEntity;
import kr.co.fastcampus.Yanabada.domain.test.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TestService {

    private final TestRepository testRepository;

    public TestInfoResponse findById(Long testId) {
        TestEntity testEntity = testRepository.findById(testId).orElseThrow();
        return TestInfoResponse.from(testEntity);
    }

    @Transactional
    public TestInfoResponse save(TestSaveRequest request) {
        TestEntity testEntity = request.toEntity();
        TestEntity result = testRepository.save(testEntity);
        return TestInfoResponse.from(result);
    }
}
