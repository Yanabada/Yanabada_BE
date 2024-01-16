package kr.co.fastcampus.yanabada.domain.payment.dto.response;

import java.time.LocalDateTime;

public record PaymentHistoryResponse(
    String productName,
    String transactionType, // 입금/출금/인출
    String bankName,
    String bankImage,
    String accountNumber,
    Long transactionAmount,
    LocalDateTime transactionTime
) {

}