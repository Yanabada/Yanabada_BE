package kr.co.fastcampus.yanabada.domain.product.dto.response;

import java.time.LocalDate;
import kr.co.fastcampus.yanabada.domain.accommodation.dto.response.AccommodationInfoResponse;
import kr.co.fastcampus.yanabada.domain.accommodation.dto.response.RoomInfoResponse;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.Accommodation;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.Room;
import kr.co.fastcampus.yanabada.domain.member.dto.response.MemberSummaryResponse;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.order.entity.Order;
import kr.co.fastcampus.yanabada.domain.product.entity.Product;
import kr.co.fastcampus.yanabada.domain.product.entity.enums.ProductStatus;
import lombok.Builder;

@Builder
public record ProductInfoResponse(
    Long id,
    MemberSummaryResponse seller,
    String description,
    Boolean canNegotiate,
    LocalDate saleEndDate,
    ProductStatus status,
    LocalDate checkInDate,
    LocalDate checkOutDate,
    Integer sellingPrice,
    Integer price,
    Integer purchasePrice,
    Integer salesPercentage,
    AccommodationInfoResponse accommodationInfo,
    RoomInfoResponse roomInfo
) {

    public static ProductInfoResponse from(Product product) {
        Order order = product.getOrder();
        Member member = order.getMember();
        Room room = order.getRoom();
        Accommodation accommodation = room.getAccommodation();

        return ProductInfoResponse.builder()
            .id(product.getId())
            .seller(MemberSummaryResponse.from(member))
            .description(product.getDescription())
            .canNegotiate(product.getCanNegotiate())
            .saleEndDate(product.getSaleEndDate())
            .status(product.getStatus())
            .checkInDate(order.getCheckInDate())
            .checkOutDate(order.getCheckOutDate())
            .sellingPrice(product.getPrice())
            .price(room.getPrice())
            .purchasePrice(order.getPrice())
            .salesPercentage(15) //TODO feature/18 merge 되면, 할인율 계산기 로직으로 변경 예정
            .accommodationInfo(AccommodationInfoResponse.from(accommodation))
            .roomInfo(RoomInfoResponse.from(room))
            .build();
    }
}
