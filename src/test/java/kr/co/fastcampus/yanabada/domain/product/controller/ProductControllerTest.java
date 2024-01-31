package kr.co.fastcampus.yanabada.domain.product.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import kr.co.fastcampus.yanabada.common.security.WithMockMember;
import kr.co.fastcampus.yanabada.common.utils.ApiTest;
import kr.co.fastcampus.yanabada.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest extends ApiTest {

    private static final MediaType MEDIA_TYPE = MediaType.APPLICATION_JSON;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ProductRepository productRepository;

    @DisplayName("[API][POST] 상품 등록 - 정상적인 등록 요청일 경우, 정상적으로 상품이 등록됩니다.")
    @Test
    @WithMockMember
    void addProduct_success() throws Exception {
        //given
        String url = "/v1/products";
        String request = """
            {
                "orderId": 1,
                "price": 10000,
                "description": "급하게 처분합니다. 네고 환영합니다!!!",
                "canNegotiate": true,
                "saleEndDate": "%s",
                "isAutoCancel": true
            }
            """.formatted(LocalDate.now().toString());

        //when
        mockMvc.perform(post(url)
                .content(request)
                .contentType(MEDIA_TYPE)
            )

            //then
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("SUCCESS"));
    }

    @DisplayName("[API][GET] 상품 조회 - 정상적인 조회 요청일 경우, 정상적으로 상품이 조회됩니다.")
    @Test
    @WithMockMember
    void getProduct_success() throws Exception {
        //given
        String url = "/v1/products/1";

        //when
        mockMvc.perform(get(url))

            //then
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("SUCCESS"));
    }

    @DisplayName("[API][GET] 상품 검색 - 정상적인 검색 요청일 경우, 정상적으로 상품이 검색됩니다.")
    @Test
    @WithMockMember
    void getProductsBySearchRequest_success() throws Exception {
        //given
        String url = "/v1/products";

        //when
        mockMvc.perform(get(url))

            //then
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("SUCCESS"));
    }
}
