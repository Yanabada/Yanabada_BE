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
    private Long accumulatedUsers;

    @Column(nullable = false)
    private Long accumulatedUsageAmount;

    @Column(nullable = false)
    private Long accumulatedDiscountAmount;

    public void deposit(int depositAmount) {
        balance += depositAmount;
    }

    public void withdrawal (int withdrawalAmount ) {
        balance += withdrawalAmount ;
    }

    public void incrementAccumulatedUsers(int userCount) {
        accumulatedUsers += userCount;
    }

    public void decrementAccumulatedUsers(int userCount) {
        accumulatedUsers -= userCount;
    }

    public void incrementAccumulatedUsageAmount(int amount) {
        accumulatedUsageAmount += amount;
    }

    public void decrementAccumulatedUsageAmount(int amount) {
        accumulatedUsageAmount -= amount;
    }

    public void incrementAccumulatedDiscountAmount(int amount) {
        accumulatedDiscountAmount += amount;
    }

    public void decrementAccumulatedDiscountAmount(int amount) {
        accumulatedDiscountAmount -= amount;
    }
}
