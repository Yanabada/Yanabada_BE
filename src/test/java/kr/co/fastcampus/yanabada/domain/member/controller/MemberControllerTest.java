package kr.co.fastcampus.yanabada.domain.member.controller;

import static kr.co.fastcampus.yanabada.common.utils.TestUtils.createMember;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kr.co.fastcampus.yanabada.common.security.WithMockMember;
import kr.co.fastcampus.yanabada.common.utils.ApiTest;
import kr.co.fastcampus.yanabada.common.utils.DatabaseClearExtension;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(DatabaseClearExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest extends ApiTest {

    private static final MediaType MEDIA_TYPE = MediaType.APPLICATION_JSON;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void setup() {
        Member member = memberRepository.save(createMember());
        System.out.println("id : " + member.getId());
    }

    @DisplayName("[API][GET] 로그인 사용자 프로필 상세 조회 테스트")
    @Test
    @WithMockMember
    void getDetails_success() throws Exception {
        //given
        String url = "/v1/members";

        //when
        mockMvc.perform(get(url))

            //then
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("SUCCESS"))
            .andExpect(jsonPath("$.data.id").value(1))
            .andExpect(jsonPath("$.data.email").value("test@email.com"))
            .andExpect(jsonPath("$.data.nickName").value("memberNickname"))
            .andExpect(jsonPath("$.data.phoneNumber").value("010-1234-1234"))
            .andExpect(jsonPath("$.data.image").value("a.jpg"));
    }

    @DisplayName("[API][PUT] 비밀번호 변경 테스트")
    @Test
    @WithMockMember
    void modifyPassword_success() throws Exception {
        //given
        String url = "/v1/members/password";
        String request = """
            {
                "password": "@@qwe123123"
            }
            """;

        //when
        mockMvc.perform(put(url)
                .content(request)
                .contentType(MEDIA_TYPE))

            //then
            .andExpect(status().isOk());

    }

    @DisplayName("[API][PUT] FcmToken 업데이트 테스트")
    @Test
    @WithMockMember
    void updateFcmToken_success() throws Exception {
        //given
        String url = "/v1/members/fcm-token";
        String request = """
            {
                "fcmToken": "adfaed814.531erdsfu#21312"
            }
            """;

        //when
        mockMvc.perform(put(url)
                .content(request)
                .contentType(MEDIA_TYPE))

            //then
            .andExpect(status().isOk());

    }


}