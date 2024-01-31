package kr.co.fastcampus.yanabada.domain.order.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kr.co.fastcampus.yanabada.common.security.WithMockMember;
import kr.co.fastcampus.yanabada.common.utils.ApiTest;
import kr.co.fastcampus.yanabada.domain.accommodation.repository.AccommodationOptionRepository;
import kr.co.fastcampus.yanabada.domain.accommodation.repository.AccommodationRepository;
import kr.co.fastcampus.yanabada.domain.accommodation.repository.RoomOptionRepository;
import kr.co.fastcampus.yanabada.domain.accommodation.repository.RoomRepository;
import kr.co.fastcampus.yanabada.domain.member.repository.MemberRepository;
import kr.co.fastcampus.yanabada.domain.order.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest extends ApiTest {

    private static final MediaType MEDIA_TYPE = MediaType.APPLICATION_JSON;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private AccommodationOptionRepository accommodationOptionRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomOptionRepository roomOptionRepository;

    @Autowired
    private OrderRepository orderRepository;

    @DisplayName("[API][GET] 판매 가능한 예약 조회 - 정상적인 요청일 경우, 정상적으로 예약이 조회됩니다.")
    @Test
    @WithMockMember
    void getSellableOrders_success() throws Exception {
        //given
        String url = "/v1/orders/can-sell";

        //when
        mockMvc.perform(get(url))

            //then
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("SUCCESS"))
            .andExpect(jsonPath("$.data[0].id").value(1));
    }

    @DisplayName("[API][GET] 예약 상세 조회 - 정상적인 요청일 경우, 정상적으로 예약이 조회됩니다.")
    @Test
    @WithMockMember
    void getOrderInfo_success() throws Exception {
        //given
        String url = "/v1/orders/1";

        //when
        mockMvc.perform(get(url))

            //then
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("SUCCESS"))
            .andExpect(jsonPath("$.data.orderId").value(1));
    }
}