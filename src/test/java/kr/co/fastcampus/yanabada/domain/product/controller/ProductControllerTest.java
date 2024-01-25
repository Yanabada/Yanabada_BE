package kr.co.fastcampus.yanabada.domain.product.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kr.co.fastcampus.yanabada.common.security.WithMockMember;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    private static final MediaType MEDIA_TYPE = MediaType.APPLICATION_JSON;

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("[API][POST] 상품 등록 - 정상적인 등록 요청일 경우, 정상적으로 상품이 등록됩니다.")
    @Test
    @WithMockMember
    void add_success() throws Exception {
        //given
        String url = "/v1/products";
        String request = """
            {
                "orderId": 1,
                "price": "400000",
                "description": "급하게 처분합니다. 네고 환영합니다!!!",
                "canNegotiate": true,
                "saleEndDate": "2024-01-26",
                "isAutoCancel": true
            }
            """;

        //when
        mockMvc.perform(post(url)
                .content(request)
                .contentType(MEDIA_TYPE)
            )

            //then
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("SUCCESS"));
    }
}
