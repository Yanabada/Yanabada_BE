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
public class YanoljaPay extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(length = 13)
    private String accountNumber;

    @Column
    private String simplePassword;

    @Column(length = 100)
    private String bankName;

    @Column(length = 200)
    private String bankImage;

    @Column(length = 500)
    private String contents;

    @Column(nullable = false)
    private Long balance;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "yanoljaPay",
        cascade = CascadeType.ALL, orphanRemoval = true
    )
    @OrderBy("transactionTime desc")
    private final List<YanoljaPayHistory> paymentHistories = new ArrayList<>();

    private YanoljaPay(
        Member member,
        String accountNumber,
        String simplePassword,
        String bankName,
        String bankImage,
        String contents,
        Long balance
    ) {
        this.member = member;
        this.accountNumber = accountNumber;
        this.simplePassword = simplePassword;
        this.bankName = bankName;
        this.bankImage = bankImage;
        this.contents = contents;
        this.balance = balance;
    }

    public static YanoljaPay create(
        Member member,
        String accountNumber,
        String simplePassword,
        String bankName,
        String bankImage,
        String contents,
        Long balance
    ) {
        return new YanoljaPay(
            member,
            accountNumber,
            simplePassword,
            bankName,
            bankImage,
            contents,
            balance
        );
    }

    public void savePassword(String password) {
        this.simplePassword = password;
    }
}