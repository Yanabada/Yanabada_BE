package kr.co.fastcampus.yanabada.domain.payment.dto.response;

import java.time.LocalDateTime;
import kr.co.fastcampus.yanabada.domain.payment.entity.YanoljaPay;
import kr.co.fastcampus.yanabada.domain.payment.entity.YanoljaPayHistory;

public record PaymentHistoryResponse(
    String productName,
    String transactionType,
    String bankName,
    String bankImage,
    String accountNumber,
    Long transactionAmount,
    LocalDateTime transactionTime
) {

    public static PaymentHistoryResponse from(YanoljaPayHistory history) {
        YanoljaPay payInfo = history.getYanoljaPay();
        return new PaymentHistoryResponse(
            payInfo.getContents(),
            history.getTransactionType().getDescription(),
            payInfo.getBankName(),
            payInfo.getBankImage(),
            payInfo.getAccountNumber(),
            history.getTransactionAmount(),
            history.getTransactionTime()
        );
    }

    public static class Builder {

        private String productName;
        private String transactionType;
        private String bankName;
        private String bankImage;
        private String accountNumber;
        private Long transactionAmount;
        private LocalDateTime transactionTime;

        public Builder productName(String productName) {
            this.productName = productName;
            return this;
        }

        public Builder transactionType(String transactionType) {
            this.transactionType = transactionType;
            return this;
        }

        public Builder bankName(String bankName) {
            this.bankName = bankName;
            return this;
        }

        public Builder bankImage(String bankImage) {
            this.bankImage = bankImage;
            return this;
        }

        public Builder accountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        public Builder transactionAmount(Long transactionAmount) {
            this.transactionAmount = transactionAmount;
            return this;
        }

        public Builder transactionTime(LocalDateTime transactionTime) {
            this.transactionTime = transactionTime;
            return this;
        }

        public PaymentHistoryResponse build() {
            return new PaymentHistoryResponse(
                productName, transactionType, bankName, bankImage,
                accountNumber, transactionAmount, transactionTime
            );
        }
    }
}