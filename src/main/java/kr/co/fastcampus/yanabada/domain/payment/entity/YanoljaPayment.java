package kr.co.fastcampus.yanabada.domain.payment.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderBy;
import java.util.ArrayList;
import java.util.List;
import kr.co.fastcampus.yanabada.common.baseentity.BaseEntity;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class YanoljaPayment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false, length = 13)
    private String accountNumber;

    @Column(nullable = false)
    private String simplePassword;

    @Column(nullable = false, length = 100)
    private String bankName;

    @Column(nullable = false, length = 200)
    private String bankImage;

    @Column(nullable = false)
    private Long balance;

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "yanoljaPayment",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @OrderBy("transactionTime asc")
    private final List<YanoljaPaymentHistory> paymentHistories = new ArrayList<>();

    private YanoljaPayment(
        Member member,
        String accountNumber,
        String simplePassword,
        String bankName,
        String bankImage,
        Long balance
    ) {
        this.member = member;
        this.accountNumber = accountNumber;
        this.simplePassword = simplePassword;
        this.bankName = bankName;
        this.bankImage = bankImage;
        this.balance = balance;
    }

    public static YanoljaPayment create(
        Member member,
        String accountNumber,
        String simplePassword,
        String bankName,
        String bankImage,
        Long balance
    ) {
        return new YanoljaPayment(
            member,
            accountNumber,
            simplePassword,
            bankName,
            bankImage,
            balance
        );
    }

    public void updateBalance(Long amount) {
        this.balance += amount;
    }
}
