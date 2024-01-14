package kr.co.fastcampus.yanabada.domain.payment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class YanabadaPaymentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long chargePrice;

    @Column(nullable = false)
    private LocalDateTime transactionTime; // 거래 시간 필드

    @ManyToOne
    @JoinColumn(name = "yanabada_payment_id", referencedColumnName = "id")
    private YanabadaPayment yanabadaPayment;

    public YanabadaPayment getYanabadaPayment() {
        return this.yanabadaPayment;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public void setChargePrice(Long chargePrice) {
        this.chargePrice = chargePrice;
    }

    public void setYanabadaPayment(YanabadaPayment yanabadaPayment) {
        this.yanabadaPayment = yanabadaPayment;
    }

    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }
}
