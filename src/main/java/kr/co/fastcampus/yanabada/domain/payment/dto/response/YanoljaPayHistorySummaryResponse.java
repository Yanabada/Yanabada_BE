package kr.co.fastcampus.yanabada.domain.payment.dto.response;

import java.time.LocalDateTime;
import kr.co.fastcampus.yanabada.domain.payment.entity.YanoljaPayHistory;
import kr.co.fastcampus.yanabada.domain.payment.entity.enums.TransactionType;
import lombok.Builder;

@Builder
public record YanoljaPayHistorySummaryResponse(
    Long historyId,
    String contents,
    Long amount,
    TransactionType type,
    LocalDateTime transactionTime
) {
    public static YanoljaPayHistorySummaryResponse from(YanoljaPayHistory history) {
        return YanoljaPayHistorySummaryResponse.builder()
            .historyId(history.getId())
            .contents(history.getContents())
            .amount(history.getTransactionAmount())
            .type(history.getTransactionType())
            .transactionTime(history.getTransactionTime())
            .build();
    }
}
