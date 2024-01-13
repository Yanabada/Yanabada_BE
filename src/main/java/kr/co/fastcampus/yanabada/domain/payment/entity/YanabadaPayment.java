package kr.co.fastcampus.yanabada.domain.payment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import kr.co.fastcampus.yanabada.common.baseentity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class YanabadaPayment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false, length = 13)
    private String accountNumber;

    @Column(nullable = false)
    private String simplePassword;

    @Column(nullable = false, length = 100)
    private String bankName;

    @Column(nullable = false, length = 200)
    private String bankImage;

    public void updateAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

}
