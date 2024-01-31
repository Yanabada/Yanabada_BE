package kr.co.fastcampus.yanabada.domain.order.service;

import static kr.co.fastcampus.yanabada.common.utils.TestUtils.createAccommodation;
import static kr.co.fastcampus.yanabada.common.utils.TestUtils.createAccommodationOption;
import static kr.co.fastcampus.yanabada.common.utils.TestUtils.createOrder;
import static kr.co.fastcampus.yanabada.common.utils.TestUtils.createRoom;
import static kr.co.fastcampus.yanabada.common.utils.TestUtils.createRoomOption;
import static kr.co.fastcampus.yanabada.common.utils.TestUtils.createSeller;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.Accommodation;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.AccommodationOption;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.Room;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.RoomOption;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.member.repository.MemberRepository;
import kr.co.fastcampus.yanabada.domain.order.dto.response.OrderInfoResponse;
import kr.co.fastcampus.yanabada.domain.order.dto.response.OrderSummaryResponse;
import kr.co.fastcampus.yanabada.domain.order.entity.Order;
import kr.co.fastcampus.yanabada.domain.order.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private OrderService orderService;

    @DisplayName("판매가능한 상품이 있을 경우, 해당 상품을 반환합니다.")
    @Test
    void getSellableOrders() {
        //given
        Member member = createSeller();
        Accommodation accommodation = createAccommodation();
        AccommodationOption accommodationOption = createAccommodationOption(accommodation);
        accommodation.registerAccommodationOption(accommodationOption);
        Room room = createRoom(accommodation);
        RoomOption roomOption = createRoomOption(room);
        room.registerRoomOption(roomOption);
        accommodation.addRoom(room);
        Order order = createOrder(room, member);
        given(memberRepository.getMember(1L)).willReturn(member);
        given(orderRepository.getSellableByMember(member)).willReturn(List.of(order));

        //when
        List<OrderSummaryResponse> sellableOrders = orderService.getSellableOrders(1L);

        //then
        assertThat(sellableOrders).isNotEmpty();
    }

    @DisplayName("정상적인 예약 상세 조회일 경우, 해당 예약을 반환합니다.")
    @Test
    void getOrderInfo() {
        //given
        Member member = createSeller();
        Accommodation accommodation = createAccommodation();
        AccommodationOption accommodationOption = createAccommodationOption(accommodation);
        accommodation.registerAccommodationOption(accommodationOption);
        Room room = createRoom(accommodation);
        RoomOption roomOption = createRoomOption(room);
        room.registerRoomOption(roomOption);
        accommodation.addRoom(room);
        Order order = createOrder(room, member);
        given(orderRepository.findById(1L)).willReturn(Optional.of(order));
        given(memberRepository.getMember(1L)).willReturn(member);

        //when
        OrderInfoResponse orderInfo = orderService.getOrderInfo(1L, 1L);

        //then
        assertThat(orderInfo).isNotNull();
    }
}