package kr.co.fastcampus.yanabada.common.utils;

import static kr.co.fastcampus.yanabada.domain.member.entity.ProviderType.EMAIL;
import static kr.co.fastcampus.yanabada.domain.member.entity.RoleType.ROLE_USER;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.Accommodation;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.AccommodationOption;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.Room;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.RoomOption;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.enums.Category;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.enums.Region;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.enums.RoomCancelPolicy;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.order.entity.Order;
import kr.co.fastcampus.yanabada.domain.order.entity.enums.OrderStatus;
import kr.co.fastcampus.yanabada.domain.order.entity.enums.PaymentType;
import kr.co.fastcampus.yanabada.domain.product.entity.Product;
import kr.co.fastcampus.yanabada.domain.product.entity.enums.ProductStatus;

public class TestUtils {

    private TestUtils() {
    }

    public static Accommodation createAccommodation() {
        return Accommodation.create(
            "testHotel",
            "testAddress",
            126.9800598249,
            37.5645211757,
            Region.SEOUL,
            "0266665555",
            "testDescription",
            Category.HOTEL_RESORT,
            "a.jpg"
        );
    }

    public static AccommodationOption createAccommodationOption(Accommodation accommodation) {
        return AccommodationOption.create(
            accommodation,
            true,
            true,
            true,
            true,
            true
        );
    }

    public static Room createRoom(Accommodation accommodation) {
        return Room.create(
            accommodation,
            "testRoom",
            45000,
            LocalTime.of(14, 0),
            LocalTime.of(11, 0),
            2,
            4,
            4.2,
            "r.jpg",
            RoomCancelPolicy.YNBD_1

        );
    }

    public static RoomOption createRoomOption(Room room) {
        return RoomOption.create(
            room,
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            false
        );
    }

    public static Order createOrder(Room room, Member member) {
        return Order.create(
            room,
            member,
            LocalDate.now().plusDays(2),
            LocalDate.now().plusDays(3),
            OrderStatus.RESERVED,
            room.getPrice(),
            "홍길동",
            "010-1234-1234",
            "홍길동",
            "010-1234-1234",
            LocalDateTime.now(),
            PaymentType.CREDIT,
            EntityCodeGenerator.generate()
        );
    }

    public static Member createMember() {
        return Member.builder()
            .email("test@test.com")
            .nickName("testNickname")
            .password("1234")
            .phoneNumber("010-1234-1234")
            .roleType(ROLE_USER)
            .image("a.jpg")
            .providerType(EMAIL)
            .build();
    }

    public static Product createProduct(Order order) {
        return Product.create(
            order,
            order.getPrice(),
            "testDescription",
            true,
            LocalDate.now().plusDays(1),
            false,
            LocalDateTime.now(),
            ProductStatus.ON_SALE
        );
    }
}
