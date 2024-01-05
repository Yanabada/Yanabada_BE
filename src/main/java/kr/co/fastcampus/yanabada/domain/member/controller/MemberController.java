package kr.co.fastcampus.yanabada.domain.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
