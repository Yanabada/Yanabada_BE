package kr.co.fastcampus.yanabada.domain.payment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import kr.co.fastcampus.yanabada.domain.payment.entity.enums.ContentsType;
import kr.co.fastcampus.yanabada.domain.payment.entity.enums.TransactionType;
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContentsType contentsType;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private Long transactionAmount; // 거래 금액 필드

    @Enumerated(EnumType.STRING) // Enum 타입으로 선언
    @Column(nullable = false)
    private TransactionType transactionType;  // 거래 유형 필드 (예: "출금", "충전")

    @Column(nullable = false)
    private LocalDateTime transactionTime; // 거래 시간 필드

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "yanolja_pay_id")
    private YanoljaPay yanoljaPay;

    private YanoljaPayHistory(
        YanoljaPay yanoljaPay,
        ContentsType contentsType,
        String contents,
        Long transactionAmount,
        TransactionType transactionType,
        LocalDateTime transactionTime
    ) {
        this.yanoljaPay = yanoljaPay;
        this.contentsType = contentsType;
        this.contents = contents;
        this.transactionAmount = transactionAmount;
        this.transactionType = transactionType;
        this.transactionTime = transactionTime;
    }

    public static YanoljaPayHistory create(
        YanoljaPay yanoljaPay,
        ContentsType contentsType,
        String contents,
        Long transactionAmount,
        TransactionType transactionType,
        LocalDateTime transactionTime
    ) {
        return new YanoljaPayHistory(
            yanoljaPay,
            contentsType,
            contents,
            transactionAmount,
            transactionType,
            transactionTime
        );
    }
}
