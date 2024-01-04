package kr.co.fastcampus.yanabada.domain.product.entity;

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
import kr.co.fastcampus.yanabada.domain.order.entity.RoomOrder;
import kr.co.fastcampus.yanabada.domain.product.entity.enums.ProductStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product")
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_order_id")
    private RoomOrder roomOrder;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Boolean canNegotiate;

    @Column(nullable = false)
    private LocalDate saleEndDate;

    @Column(nullable = false)
    private Boolean isAutoCancel;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    private Product(
        RoomOrder roomOrder,
        Integer price,
        String description,
        Boolean canNegotiate,
        LocalDate saleEndDate,
        Boolean isAutoCancel,
        ProductStatus status
    ) {
        this.roomOrder = roomOrder;
        this.price = price;
        this.description = description;
        this.canNegotiate = canNegotiate;
        this.saleEndDate = saleEndDate;
        this.isAutoCancel = isAutoCancel;
        this.status = status;
    }

    public static Product create(
        RoomOrder roomOrder,
        Integer price,
        String description,
        Boolean canNegotiate,
        LocalDate saleEndDate,
        Boolean isAutoCancel,
        ProductStatus status
    ) {
        return new Product(
            roomOrder,
            price,
            description,
            canNegotiate,
            saleEndDate,
            isAutoCancel,
            status
        );
    }
}
