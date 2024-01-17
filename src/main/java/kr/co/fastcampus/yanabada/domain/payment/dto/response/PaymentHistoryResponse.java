package kr.co.fastcampus.yanabada.domain.payment.dto.response;

import java.time.LocalDateTime;
import kr.co.fastcampus.yanabada.domain.payment.entity.YanoljaPay;
import kr.co.fastcampus.yanabada.domain.payment.entity.YanoljaPayHistory;
import lombok.Builder;

@Builder
public record PaymentHistoryResponse(
    String contents,
    String transactionType,
    String bankName,
    String bankImage,
    String accountNumber,
    Long transactionAmount,
    LocalDateTime transactionTime
) {

    public static PaymentHistoryResponse from(YanoljaPayHistory history) {
        YanoljaPay payInfo = history.getYanoljaPay();
        return PaymentHistoryResponse.builder()
            .contents(payInfo.getContents())
            .transactionType(history.getTransactionType().getDescription())
            .bankName(payInfo.getBankName())
            .bankImage(payInfo.getBankImage())
            .accountNumber(payInfo.getAccountNumber())
            .transactionAmount(history.getTransactionAmount())
            .transactionTime(history.getTransactionTime())
            .build();
    }
}