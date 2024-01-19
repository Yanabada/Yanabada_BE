package kr.co.fastcampus.yanabada.domain.payment.dto.response;

import java.util.List;
import lombok.Builder;
import org.springframework.data.domain.Page;

@Builder
public record PurchaseTradePageResponse(
    List<PurchaseTradeSummaryResponse> purchaseTrades,
    int pageNum,
    int pageSize,
    int totalPages,
    boolean isFirst,
    boolean isLast
) {

    public static PurchaseTradePageResponse from(Page<PurchaseTradeSummaryResponse> responsePage) {
        return PurchaseTradePageResponse.builder()
            .purchaseTrades(responsePage.getContent())
            .pageNum(responsePage.getNumber())
            .pageSize(responsePage.getSize())
            .totalPages(responsePage.getTotalPages())
            .isFirst(responsePage.isFirst())
            .isLast(responsePage.isLast())
            .build();
    }
}
