package kr.co.fastcampus.yanabada.domain.payment.entity.enums;

public enum TransactionType {

    DEPOSIT("입금"),
    WITHDRAW("출금"),
    DISBURSEMENT("인출"),
    CHARGE("충전");

    private final String description;

    TransactionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}