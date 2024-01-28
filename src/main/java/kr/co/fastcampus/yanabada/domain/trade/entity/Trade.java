package kr.co.fastcampus.yanabada.domain.trade.entity;

import static kr.co.fastcampus.yanabada.domain.trade.entity.enums.TradeStatus.CANCELED;
import static kr.co.fastcampus.yanabada.domain.trade.entity.enums.TradeStatus.COMPLETED;
import static kr.co.fastcampus.yanabada.domain.trade.entity.enums.TradeStatus.REJECTED;

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
import java.time.LocalDateTime;
import kr.co.fastcampus.yanabada.common.baseentity.BaseEntity;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.order.entity.enums.PaymentType;
import kr.co.fastcampus.yanabada.domain.trade.entity.enums.TradeStatus;
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
    private Integer price; //원가

    @Column(nullable = false)
    private Integer sellingPrice; //판매가

    @Column(nullable = false)
    private Integer fee; //수수료

    @Column(nullable = false)
    private Integer point; // 구매자가 사용한 포인트

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TradeStatus status;

    @Column(nullable = false)
    private LocalDateTime registeredDate;

    @Column(nullable = false)
    private Boolean hasSellerDeleted;

    @Column(nullable = false)
    private Boolean hasBuyerDeleted;

    private Trade(
        Product product,
        Member seller,
        Member buyer,
        String reservationPersonName,
        String reservationPersonPhoneNumber,
        String userPersonName,
        String userPersonPhoneNumber,
        Integer price,
        Integer sellingPrice,
        Integer fee,
        Integer point,
        PaymentType paymentType,
        String code,
        TradeStatus status,
        LocalDateTime registeredDate,
        Boolean hasSellerDeleted,
        Boolean hasBuyerDeleted

    ) {
        this.product = product;
        this.seller = seller;
        this.buyer = buyer;
        this.reservationPersonName = reservationPersonName;
        this.reservationPersonPhoneNumber = reservationPersonPhoneNumber;
        this.userPersonName = userPersonName;
        this.userPersonPhoneNumber = userPersonPhoneNumber;
        this.price = price;
        this.sellingPrice = sellingPrice;
        this.fee = fee;
        this.point = point;
        this.paymentType = paymentType;
        this.code = code;
        this.status = status;
        this.registeredDate = registeredDate;
        this.hasSellerDeleted = hasSellerDeleted;
        this.hasBuyerDeleted = hasBuyerDeleted;
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
        Integer sellingPrice,
        Integer fee,
        Integer point,
        PaymentType paymentType,
        String code,
        TradeStatus status,
        LocalDateTime registeredDate
    ) {
        return new Trade(
            product,
            seller,
            buyer,
            reservationPersonName,
            reservationPersonPhoneNumber,
            userPersonName,
            userPersonPhoneNumber,
            price,
            sellingPrice,
            fee,
            point,
            paymentType,
            code,
            status,
            registeredDate,
            false,
            false
        );
    }

    public void complete() {
        status = COMPLETED;
    }

    public void reject() {
        status = REJECTED;
    }

    public void cancel() {
        status = CANCELED;
    }

    public void deleteBySeller() {
        hasSellerDeleted = true;
    }

    public void deleteByBuyer() {
        hasBuyerDeleted = true;
    }
}
