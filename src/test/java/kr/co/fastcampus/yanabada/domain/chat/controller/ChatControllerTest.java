package kr.co.fastcampus.yanabada.domain.chat.controller;

import static kr.co.fastcampus.yanabada.common.response.ErrorCode.CANNOT_NEGOTIATE_OWN_PRODUCT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.restassured.path.json.JsonPath;
import kr.co.fastcampus.yanabada.common.security.WithMockMember;
import kr.co.fastcampus.yanabada.common.utils.ApiTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;


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

    @DisplayName("[API][PUT] 채팅방 수정 성공")
    @Test
    @WithMockMember
    void modifyChatRoom_success() throws Exception {
        //given
        String url = "/v1/chats";
        String request = """
            {
                "productId": 1,
                "buyerId": 2
            }
            """;

        ResultActions resultActions = mockMvc.perform(post(url)
            .content(request)
            .contentType(MEDIA_TYPE)
        );
        MvcResult mvcResult = resultActions.andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        JsonPath jsonPath = JsonPath.from(responseBody);
        String chatRoomCode = jsonPath.getString("data.chatRoomCode");

        String secondUrl = "/v1/chats";
        String secondRequest = """
            {
                "chatRoomCode": "%s"
            }
            """.formatted(chatRoomCode);

        //when
        mockMvc.perform(put(secondUrl)
                .content(secondRequest)
                .contentType(MEDIA_TYPE)
            )
            //then
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$.status").value("SUCCESS"))
            .andExpect(jsonPath("$.data.chatRoomCode").value(chatRoomCode))
            .andExpect(jsonPath("$.data.updatedMemberId").value(1L));
    }

    @DisplayName("[API][DELETE] 채팅방 삭제 성공")
    @Test
    @WithMockMember
    void modifyOrDeleteChatRoom_success() throws Exception {
        //given
        String url = "/v1/chats";
        String request = """
            {
                "productId": 1,
                "buyerId": 2
            }
            """;

        ResultActions resultActions = mockMvc.perform(post(url)
            .content(request)
            .contentType(MEDIA_TYPE)
        );
        MvcResult mvcResult = resultActions.andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        JsonPath jsonPath = JsonPath.from(responseBody);
        String chatRoomCode = jsonPath.getString("data.chatRoomCode");

        String secondUrl = "/v1/chats";
        String secondRequest = """
            {
                "chatRoomCode": "%s"
            }
            """.formatted(chatRoomCode);

        //when
        mockMvc.perform(delete(secondUrl)
                .content(secondRequest)
                .contentType(MEDIA_TYPE)
            )
            //then
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$.status").value("SUCCESS"))
            .andExpect(jsonPath("$.data.chatRoomCode").value(chatRoomCode))
            .andExpect(jsonPath("$.data.updatedMemberId").value(1L));
    }
}
