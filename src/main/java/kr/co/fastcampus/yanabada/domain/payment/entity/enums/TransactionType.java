package kr.co.fastcampus.yanabada.domain.payment.entity.enums;

public enum TransactionType {
    CHARGE("충전"),
    WITHDRAW("출금");

    private final String description;

    TransactionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}