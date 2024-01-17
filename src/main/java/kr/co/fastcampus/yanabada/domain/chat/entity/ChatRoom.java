package kr.co.fastcampus.yanabada.domain.chat.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import kr.co.fastcampus.yanabada.common.baseentity.BaseEntity;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.product.entity.Product;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ChatRoom extends BaseEntity {

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

    @Column(name = "chat_room_code", nullable = false)
    private String code;

    private LocalDateTime sellerLastCheckTime;

    private LocalDateTime buyerLastCheckTime;

    private Boolean hasSellerLeft;

    private Boolean hasBuyerLeft;

    @OneToMany(
        fetch = FetchType.LAZY, mappedBy = "chatRoom",
        cascade = CascadeType.ALL, orphanRemoval = true
    )
    @OrderBy("sendDateTime desc")
    private final List<ChatMessage> messages = new ArrayList<>();

    private ChatRoom(
        Product product,
        Member seller,
        Member buyer,
        String code,
        LocalDateTime sellerLastCheckTime,
        LocalDateTime buyerLastCheckTime,
        Boolean hasSellerLeft,
        Boolean hasBuyerLeft
    ) {
        this.product = product;
        this.seller = seller;
        this.buyer = buyer;
        this.code = code;
        this.sellerLastCheckTime = sellerLastCheckTime;
        this.buyerLastCheckTime = buyerLastCheckTime;
        this.hasSellerLeft = hasSellerLeft;
        this.hasBuyerLeft = hasBuyerLeft;
    }

    public static ChatRoom create(
        Product product,
        Member seller,
        Member buyer,
        String code,
        LocalDateTime sellerLastCheckTime,
        LocalDateTime buyerLastCheckTime
    ) {
        return new ChatRoom(
            product,
            seller,
            buyer,
            code,
            sellerLastCheckTime,
            buyerLastCheckTime,
            false,
            false
        );
    }

    public void addChatMessage(ChatMessage message) {
        messages.add(message);
    }

    public void updateSellerLastCheckTime(LocalDateTime lastCheckTime) {
        this.sellerLastCheckTime = lastCheckTime;
    }

    public void updateBuyerLastCheckTime(LocalDateTime lastCheckTime) {
        this.buyerLastCheckTime = lastCheckTime;
    }

    public void updateHasSellerLeft(Boolean hasLeft) {
        this.hasSellerLeft = hasLeft;
    }

    public void updateHasBuyerLeft(Boolean hasLeft) {
        this.hasBuyerLeft = hasLeft;
    }
}
