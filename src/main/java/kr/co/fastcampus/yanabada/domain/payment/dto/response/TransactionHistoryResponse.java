package kr.co.fastcampus.yanabada.domain.payment.dto.response;

import java.time.LocalDateTime;

public record TransactionHistoryResponse(
    String name,
    String image,
    String accountNum,
    Long price,
    LocalDateTime time
) {

}
