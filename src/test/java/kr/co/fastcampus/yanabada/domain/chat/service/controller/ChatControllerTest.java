package kr.co.fastcampus.yanabada.domain.chat.service.controller;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.CANNOT_NEGOTIATE_OWN_PRODUCT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kr.co.fastcampus.yanabada.common.security.WithMockMember;
import kr.co.fastcampus.yanabada.common.utils.ApiTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


@AutoConfigureMockMvc
public class ChatControllerTest extends ApiTest {

    private static final MediaType MEDIA_TYPE = MediaType.APPLICATION_JSON;

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("[API][POST] 채팅방 생성 성공")
    @Test
    @WithMockMember
    void add_chatRoom_success() throws Exception {
        //given
        String url = "/v1/chats";
        String request = """
            {
                "productId": 1,
                "buyerId": 2
            }
            """;

        //when
        mockMvc.perform(post(url)
                .content(request)
                .contentType(MEDIA_TYPE)
            )

            //then
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$.status").value("SUCCESS"));
    }

    @DisplayName("[API][POST] 채팅방 생성 실패")
    @Test
    @WithMockMember
    void add_chatRoom_fail() throws Exception {
        //given
        String url = "/v1/chats";
        String request = """
            {
                "productId": 1,
                "buyerId": 1
            }
            """;

        //when
        mockMvc.perform(post(url)
                .content(request)
                .contentType(MEDIA_TYPE)
            )

            //then
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status").value("FAIL"))
            .andExpect(jsonPath("$.errorMessage")
                .value(CANNOT_NEGOTIATE_OWN_PRODUCT.getMessage()));
    }
}
