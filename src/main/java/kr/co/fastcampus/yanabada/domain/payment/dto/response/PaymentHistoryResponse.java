package kr.co.fastcampus.yanabada.domain.payment.dto.response;

import java.time.LocalDateTime;
import kr.co.fastcampus.yanabada.domain.payment.entity.YanoljaPay;
import kr.co.fastcampus.yanabada.domain.payment.entity.YanoljaPayHistory;

public record PaymentHistoryResponse(
    String productName,
    String transactionType, // 입금/출금/인출
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
}