package kr.co.fastcampus.yanabada.domain.order.service;

import kr.co.fastcampus.yanabada.common.exception.RoomNotFoundException;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.Room;
import kr.co.fastcampus.yanabada.domain.accommodation.repository.RoomRepository;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.member.repository.MemberRepository;
import kr.co.fastcampus.yanabada.domain.order.dto.request.OrderSaveRequest;
import kr.co.fastcampus.yanabada.domain.order.dto.response.OrderInfoResponse;
import kr.co.fastcampus.yanabada.domain.order.entity.RoomOrder;
import kr.co.fastcampus.yanabada.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final RoomRepository roomRepository;

    private final MemberRepository memberRepository;

    @Transactional
    public void saveOrder(OrderSaveRequest request) {
        Room room = roomRepository.getRoom(request.roomId());
        Member member = memberRepository.getMember(request.memberId());
        RoomOrder roomOrder = orderRepository.save(request.toEntity(room, member));
    }

    @Transactional(readOnly = true)
    public OrderInfoResponse getOrderInfo(Long orderId) {
        RoomOrder roomOrder = orderRepository.findById(orderId)
            .orElseThrow(RoomNotFoundException::new);

        return OrderInfoResponse.from(roomOrder);
    }
}
