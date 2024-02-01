package kr.co.fastcampus.yanabada.domain.payment.dto.response;

import kr.co.fastcampus.yanabada.domain.payment.entity.YanoljaPay;
import kr.co.fastcampus.yanabada.domain.payment.entity.YanoljaPayHistory;
import kr.co.fastcampus.yanabada.domain.payment.entity.enums.TransactionType;
import lombok.Builder;

@Builder
public record YanoljaPayHistoryInfoResponse(
    TransactionType type,
    Long amount,
    String bankName,
    String bankAccount,
    Long balance
) {
    public static YanoljaPayHistoryInfoResponse from(YanoljaPayHistory yanoljaPayHistory) {
        YanoljaPay yanoljaPay = yanoljaPayHistory.getYanoljaPay();

        return YanoljaPayHistoryInfoResponse.builder()
            .type(yanoljaPayHistory.getTransactionType())
            .amount(yanoljaPayHistory.getTransactionAmount())
            .bankName(yanoljaPay.getBankName())
            .bankAccount(yanoljaPay.getAccountNumber())
            .balance(yanoljaPay.getBalance())
            .build();
    }
}
