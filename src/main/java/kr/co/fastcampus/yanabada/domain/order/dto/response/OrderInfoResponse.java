package kr.co.fastcampus.yanabada.domain.order.dto.response;

import java.time.LocalDate;
import java.util.Map;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.RoomOption;
import kr.co.fastcampus.yanabada.domain.order.entity.Order;

public record OrderInfoResponse(
    Long orderId,
    LocalDate orderDate,
    String accommodationName,
    String accommodationImage,
    String roomName,
    LocalDate checkInDate,
    LocalDate checkOutDate,
    Integer price,
    Integer adultCount,
    Integer childCount,
    Integer maxHeadCount,
    String reservationPersonName,
    String reservationPersonPhoneNumber,
    String userPersonName,
    String userPersonPhoneNumber,
    Map<String, Boolean> roomOptions,
    Integer totalPayment,
    String paymentMethod
) {
    public static OrderInfoResponse from(Order order) {
        RoomOption roomOption = order.getRoom().getRoomOption();
        Map<String, Boolean> roomOptionsMap = Map.ofEntries(
            Map.entry("canPark", roomOption.getCanPark()),
            Map.entry("isPartyRoom", roomOption.getIsPartyRoom()),
            Map.entry("canAccompanyPet", roomOption.getCanAccompanyPet()),
            Map.entry("isKidsRoom", roomOption.getIsKidsRoom()),
            Map.entry("isCityView", roomOption.getIsCityView()),
            Map.entry("isOceanView", roomOption.getIsOceanView()),
            Map.entry("hasPc", roomOption.getHasPc()),
            Map.entry("hasOtt", roomOption.getHasOtt()),
            Map.entry("hasBathtub", roomOption.getHasBathtub()),
            Map.entry("hasAmenity", roomOption.getHasAmenity()),
            Map.entry("hasBreakfast", roomOption.getHasBreakfast()),
            Map.entry("canCook", roomOption.getCanCook()),
            Map.entry("isNoKids", roomOption.getIsNoKids())
        );

        return new OrderInfoResponse(
            order.getId(),
            order.getCheckInDate(),
            order.getRoom().getAccommodation().getName(),
            order.getRoom().getAccommodation().getImage(),
            order.getRoom().getName(),
            order.getCheckInDate(),
            order.getCheckOutDate(),
            order.getPrice(),
            order.getAdultCount(),
            order.getChildCount(),
            order.getRoom().getMaxHeadCount(),
            order.getReservationPersonName(),
            order.getReservationPersonPhoneNumber(),
            order.getUserPersonName(),
            order.getUserPersonPhoneNumber(),
            roomOptionsMap,
            order.getPrice(),
            order.getPaymentType().name()
        );
    }
}
