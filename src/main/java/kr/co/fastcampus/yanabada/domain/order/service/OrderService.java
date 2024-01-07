package kr.co.fastcampus.yanabada.domain.order.service;

import kr.co.fastcampus.yanabada.common.exception.RoomNotFoundException;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.Room;
import kr.co.fastcampus.yanabada.domain.accommodation.repository.RoomRepository;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.member.repository.MemberRepository;
import kr.co.fastcampus.yanabada.domain.order.dto.request.OrderSaveRequest;
import kr.co.fastcampus.yanabada.domain.order.dto.response.ReservationDetailsDto;
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
    public ReservationDetailsDto getReservationDetails(Long reservationId) {
        RoomOrder roomOrder = orderRepository.findById(reservationId)
            .orElseThrow(RoomNotFoundException::new);

        return ReservationDetailsDto.builder()
            .reservationId(roomOrder.getId())
            .reservationDate(roomOrder.getCheckInDate())
            .accommodationName(roomOrder.getRoom().getAccommodation().getName())
            .accommodationImage(roomOrder.getRoom().getAccommodation().getImage())
            .roomName(roomOrder.getRoom().getName())
            .checkInDate(roomOrder.getCheckInDate())
            .checkOutDate(roomOrder.getCheckOutDate())
            .price(roomOrder.getPrice())
            .adultCount(roomOrder.getAdultCount())
            .childCount(roomOrder.getChildCount())
            .maxHeadCount(roomOrder.getRoom().getMaxHeadCount())
            .reservationPersonName(roomOrder.getReservationPersonName())
            .reservationPersonPhoneNumber(roomOrder.getReservationPersonPhoneNumber())
            .userPersonName(roomOrder.getUserPersonName())
            .userPersonPhoneNumber(roomOrder.getUserPersonPhoneNumber())
            // RoomOptions는 현재 entity가 구현되지 않은 상태라 맵을 추가하지 않았습니다.
            .totalPayment(roomOrder.getPrice())
            .paymentMethod(roomOrder.getPaymentType().name())
            .build();
    }
}
