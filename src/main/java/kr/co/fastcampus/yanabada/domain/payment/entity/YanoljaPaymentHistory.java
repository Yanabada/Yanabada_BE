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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class YanoljaPaymentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long chargePrice;

    @Column(nullable = false)
    private LocalDateTime transactionTime; // 거래 시간 필드

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "yanabada_payment_id")
    private YanoljaPayment yanoljaPayment;

    private YanoljaPaymentHistory(
        YanoljaPayment yanoljaPayment,
        Long chargePrice,
        LocalDateTime transactionTime
    ) {
        this.yanoljaPayment = yanoljaPayment;
        this.chargePrice = chargePrice;
        this.transactionTime = transactionTime;
    }

    public static YanoljaPaymentHistory create(
        YanoljaPayment yanoljaPayment,
        Long chargePrice,
        LocalDateTime transactionTime
    ) {
        return new YanoljaPaymentHistory(
            yanoljaPayment,
            chargePrice,
            transactionTime
        );
    }
}
