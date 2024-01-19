package kr.co.fastcampus.yanabada.domain.order.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import kr.co.fastcampus.yanabada.common.exception.AccessForbiddenException;
import kr.co.fastcampus.yanabada.common.exception.OrderNotFoundException;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.Room;
import kr.co.fastcampus.yanabada.domain.accommodation.repository.RoomRepository;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.member.repository.MemberRepository;
import kr.co.fastcampus.yanabada.domain.order.dto.request.OrderSaveRequest;
import kr.co.fastcampus.yanabada.domain.order.dto.response.OrderInfoResponse;
import kr.co.fastcampus.yanabada.domain.order.dto.response.OrderSummaryResponse;
import kr.co.fastcampus.yanabada.domain.order.entity.Order;
import kr.co.fastcampus.yanabada.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    public static final String CRON_SCHEDULING = "0 0 0 * * *";

    private final OrderRepository orderRepository;

    private final RoomRepository roomRepository;

    private final MemberRepository memberRepository;

    @Transactional
    public void saveOrder(OrderSaveRequest request) {
        Room room = roomRepository.getRoom(request.roomId());
        Member member = memberRepository.getMember(request.memberId());
        Order order = orderRepository.save(request.toEntity(room, member, LocalDateTime.now()));
    }

    @Transactional(readOnly = true)
    public List<OrderSummaryResponse> getSellableOrders(Long memberId) {
        Member member = memberRepository.getMember(memberId);

        return orderRepository.getSellableByMember(member)
            .stream()
            .map(OrderSummaryResponse::from)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderInfoResponse getOrderInfo(Long orderId, Long currentUserId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(OrderNotFoundException::new);

        if (!order.getMember().getId().equals(currentUserId)) {
            throw new AccessForbiddenException();
        }

        return OrderInfoResponse.from(order);
    }

    @Transactional
    @Scheduled(cron = CRON_SCHEDULING)
    public void updateOrdersUsed() {
        orderRepository.getByCheckInDateExpired()
            .forEach(Order::use);
    }
}