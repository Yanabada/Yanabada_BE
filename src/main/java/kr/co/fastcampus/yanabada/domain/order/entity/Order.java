package kr.co.fastcampus.yanabada.domain.order.entity;

import static kr.co.fastcampus.yanabada.domain.order.entity.enums.OrderStatus.USED;
import static kr.co.fastcampus.yanabada.domain.order.entity.enums.OrderStatus.CANCELED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import kr.co.fastcampus.yanabada.common.baseentity.BaseEntity;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.Room;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.order.entity.enums.OrderStatus;
import kr.co.fastcampus.yanabada.domain.order.entity.enums.PaymentType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "room_order")
@Entity
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private LocalDate checkInDate;

    @Column(nullable = false)
    private LocalDate checkOutDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private String reservationPersonName;

    @Column(nullable = false)
    private String reservationPersonPhoneNumber;

    @Column(nullable = false)
    private String userPersonName;

    @Column(nullable = false)
    private String userPersonPhoneNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Column(nullable = false)
    private String code;

    private Order(
        Room room,
        Member member,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        OrderStatus status,
        Integer price,
        String reservationPersonName,
        String reservationPersonPhoneNumber,
        String userPersonName,
        String userPersonPhoneNumber,
        PaymentType paymentType,
        String code
    ) {
        this.room = room;
        this.member = member;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = status;
        this.price = price;
        this.reservationPersonName = reservationPersonName;
        this.reservationPersonPhoneNumber = reservationPersonPhoneNumber;
        this.userPersonName = userPersonName;
        this.userPersonPhoneNumber = userPersonPhoneNumber;
        this.paymentType = paymentType;
        this.code = code;
    }

    public static Order create(
        Room room,
        Member member,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        OrderStatus status,
        Integer price,
        String reservationPersonName,
        String reservationPersonPhoneNumber,
        String userPersonName,
        String userPersonPhoneNumber,
        PaymentType paymentType,
        String code
    ) {
        return new Order(
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
            code
        );
    }

    public void use() {
        status = USED;
    }

    public void cancel() {
        status = CANCELED;
    }
}
