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
import java.util.ArrayList;
import java.util.List;
import kr.co.fastcampus.yanabada.common.baseentity.BaseEntity;
import kr.co.fastcampus.yanabada.common.exception.NotEnoughYanoljaPayBalanceException;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class YanoljaPay extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column
    private String accountNumber;

    @Column
    private String simplePassword;

    @Column
    private String bankName;

    @Column(nullable = false)
    private Long balance;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "yanoljaPay",
        cascade = CascadeType.ALL, orphanRemoval = true
    )
    private final List<YanoljaPayHistory> histories = new ArrayList<>();

    private YanoljaPay(
        Member member,
        String accountNumber,
        String simplePassword,
        String bankName,
        Long balance
    ) {
        this.member = member;
        this.accountNumber = accountNumber;
        this.simplePassword = simplePassword;
        this.bankName = bankName;
        this.balance = balance;
    }

    public static YanoljaPay create(
        Member member,
        String accountNumber,
        String simplePassword,
        String bankName,
        Long balance
    ) {
        return new YanoljaPay(
            member,
            accountNumber,
            simplePassword,
            bankName,
            balance
        );
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setSimplePassword(String simplePassword) {
        this.simplePassword = simplePassword;
    }

    public void deposit(long amount) {
        balance += amount;
    }

    public void withdraw(long amount) {
        if (balance < amount) {
            throw new NotEnoughYanoljaPayBalanceException();
        }
        balance -= amount;
    }

    public void charge(long amount) {
        balance += amount;
    }

    public void disburse(long amount) {
        if (balance < amount) {
            throw new NotEnoughYanoljaPayBalanceException();
        }
        balance -= amount;
    }
}