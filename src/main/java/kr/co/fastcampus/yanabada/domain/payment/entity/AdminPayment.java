package kr.co.fastcampus.yanabada.domain.payment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import kr.co.fastcampus.yanabada.common.baseentity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class AdminPayment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long balance;

    @Column(nullable = false)
    private Long accumulatedUsageAmount;

    @Column(nullable = false)
    private Long accumulatedDiscountAmount;

    public void deposit(long depositAmount) {
        balance += depositAmount;
    }

    public void withdraw(long withdrawalAmount) {
        balance -= withdrawalAmount;
    }

    public void increaseAccumulatedUsageAmount(long amount) {
        accumulatedUsageAmount += amount;
    }

    public void decreaseAccumulatedUsageAmount(long amount) {
        accumulatedUsageAmount -= amount;
    }

    public void increaseAccumulatedDiscountAmount(long amount) {
        accumulatedDiscountAmount += amount;
    }

    public void decreaseAccumulatedDiscountAmount(long amount) {
        accumulatedDiscountAmount -= amount;
    }
}
