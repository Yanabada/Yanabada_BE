package kr.co.fastcampus.yanabada.domain.payment.entity;

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
import kr.co.fastcampus.yanabada.common.baseentity.BaseEntity;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.order.entity.enums.PaymentType;
import kr.co.fastcampus.yanabada.domain.payment.entity.enums.TradeStatus;
import kr.co.fastcampus.yanabada.domain.product.entity.Product;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "trade")
@Entity
public class Trade extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Member seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private Member buyer;

    @Column(nullable = false)
    private String reservationPersonName;

    @Column(nullable = false)
    private String reservationPersonPhoneNumber;

    @Column(nullable = false)
    private String userPersonName;

    @Column(nullable = false)
    private String userPersonPhoneNumber;

    @Column(nullable = false)
    private Integer price; //판매가

    @Column(nullable = false)
    private Integer fee; //수수료

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TradeStatus status;

    private Trade(
        Product product,
        Member seller,
        Member buyer,
        String reservationPersonName,
        String reservationPersonPhoneNumber,
        String userPersonName,
        String userPersonPhoneNumber,
        Integer sellingPrice,
        Integer fee,
        PaymentType paymentType,
        String code,
        TradeStatus status
    ) {
        this.product = product;
        this.buyer = buyer;
        this.seller = seller;
        this.reservationPersonName = reservationPersonName;
        this.reservationPersonPhoneNumber = reservationPersonPhoneNumber;
        this.userPersonName = userPersonName;
        this.userPersonPhoneNumber = userPersonPhoneNumber;
        this.price = sellingPrice;
        this.fee = fee;
        this.paymentType = paymentType;
        this.code = code;
        this.status = status;
    }

    public static Trade create(
        Product product,
        Member seller,
        Member buyer,
        String reservationPersonName,
        String reservationPersonPhoneNumber,
        String userPersonName,
        String userPersonPhoneNumber,
        Integer price,
        Integer fee,
        PaymentType paymentType,
        String code,
        TradeStatus status
    ) {
        return new Trade(
            product,
            buyer,
            seller,
            reservationPersonName,
            reservationPersonPhoneNumber,
            userPersonName,
            userPersonPhoneNumber,
            price,
            fee,
            paymentType,
            code,
            status
        );
    }
}
