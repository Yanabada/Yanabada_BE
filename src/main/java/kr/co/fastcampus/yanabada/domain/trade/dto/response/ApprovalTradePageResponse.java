package kr.co.fastcampus.yanabada.domain.trade.dto.response;

import java.util.List;
import lombok.Builder;
import org.springframework.data.domain.Page;

@Builder
public record ApprovalTradePageResponse(
    List<ApprovalTradeSummaryResponse> approvalTrades,
    int pageNum,
    int pageSize,
    int totalPages,
    boolean isFirst,
    boolean isLast
) {

    public static ApprovalTradePageResponse from(Page<ApprovalTradeSummaryResponse> responsePage) {
        return ApprovalTradePageResponse.builder()
            .approvalTrades(responsePage.getContent())
            .pageNum(responsePage.getNumber())
            .pageSize(responsePage.getSize())
            .totalPages(responsePage.getTotalPages())
            .isFirst(responsePage.isFirst())
            .isLast(responsePage.isLast())
            .build();
    }
}
