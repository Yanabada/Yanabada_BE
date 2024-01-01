package kr.co.fastcampus.yanabada.domain.test.controller;

import kr.co.fastcampus.yanabada.common.response.ResponseBody;
import kr.co.fastcampus.yanabada.domain.test.dto.TestInfoResponse;
import kr.co.fastcampus.yanabada.domain.test.dto.TestSaveRequest;
import kr.co.fastcampus.yanabada.domain.test.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tests")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping("/{testId}")
    public ResponseBody<TestInfoResponse> getTest(@PathVariable Long testId) {
        return ResponseBody.ok(testService.findById(testId));
    }

    @PostMapping
    public ResponseBody<TestInfoResponse> addTest(@RequestBody TestSaveRequest request) {
        return ResponseBody.ok(testService.save(request));
    }

}
