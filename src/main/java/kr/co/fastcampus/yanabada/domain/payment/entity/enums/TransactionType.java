package kr.co.fastcampus.yanabada.domain.payment.entity.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TransactionType {
    DEPOSIT, //입금
    WITHDRAW, //출금
    DISBURSEMENT, //인출
    CHARGE //충전
}