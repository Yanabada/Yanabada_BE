package kr.co.fastcampus.yanabada.domain.order.dto.request;

import java.time.LocalDate;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.Room;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.order.entity.RoomOrder;
import kr.co.fastcampus.yanabada.domain.order.entity.enums.OrderStatus;
import kr.co.fastcampus.yanabada.domain.order.entity.enums.PaymentType;

public record OrderSaveRequest(
    Long roomId,
    Long memberId,
    LocalDate checkInDate,
    LocalDate checkOutDate,
    OrderStatus status,
    Integer price,
    String reservationPersonName,
    String reservationPersonPhoneNumber,
    String userPersonName,
    String userPersonPhoneNumber,
    PaymentType paymentType,
    Integer adultCount,
    Integer childCount
) {

    public RoomOrder toEntity(Room room, Member member) {
        return RoomOrder.create(
            room,
            member,
            checkInDate,
            checkOutDate,
            status,
            price,
            reservationPersonName,
            reservationPersonPhoneNumber,
            userPersonName,
            userPersonPhoneNumber,
            paymentType,
            adultCount,
            childCount
        );
    }
}
