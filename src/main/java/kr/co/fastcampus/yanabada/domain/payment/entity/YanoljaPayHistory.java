package kr.co.fastcampus.yanabada.domain.payment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class YanoljaPayHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long chargePrice;

    @Column(nullable = false)
    private LocalDateTime transactionTime; // 거래 시간 필드

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "yanolja_pay_id")
    private YanoljaPay yanoljaPay;

    public Member getMember() {
        return yanoljaPay != null ? yanoljaPay.getMember() : null;
    }


    private YanoljaPayHistory(
        YanoljaPay yanoljaPay,
        Long chargePrice,
        LocalDateTime transactionTime
    ) {
        this.yanoljaPay = yanoljaPay;
        this.chargePrice = chargePrice;
        this.transactionTime = transactionTime;
    }


    public static YanoljaPayHistory create(

        YanoljaPay yanoljaPay,
        Long chargePrice,
        LocalDateTime transactionTime
    ) {
        return new YanoljaPayHistory(
            yanoljaPay,
            chargePrice,
            transactionTime
        );
    }
}
