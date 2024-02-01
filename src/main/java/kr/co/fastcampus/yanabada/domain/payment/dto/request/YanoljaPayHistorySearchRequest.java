package kr.co.fastcampus.yanabada.domain.payment.dto.request;

import java.util.List;
import kr.co.fastcampus.yanabada.domain.payment.entity.enums.TransactionType;

public record YanoljaPayHistorySearchRequest(
    List<TransactionType> types,
    Integer page,
    Integer size
) {

}
