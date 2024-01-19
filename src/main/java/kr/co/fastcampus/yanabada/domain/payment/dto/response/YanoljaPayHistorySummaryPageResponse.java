package kr.co.fastcampus.yanabada.domain.payment.dto.response;

import java.util.List;
import java.util.stream.Collectors;
import kr.co.fastcampus.yanabada.domain.payment.entity.YanoljaPayHistory;
import lombok.Builder;
import org.springframework.data.domain.Page;

@Builder
public record YanoljaPayHistorySummaryPageResponse(
    List<YanoljaPayHistorySummaryResponse> histories,
    long totalElements,
    int totalPages
) {

    public static YanoljaPayHistorySummaryPageResponse from(Page<YanoljaPayHistory> page) {
        return YanoljaPayHistorySummaryPageResponse.builder()
            .histories(
                page.getContent()
                    .stream()
                    .map(YanoljaPayHistorySummaryResponse::from)
                    .collect(Collectors.toList())
            )
            .totalElements(page.getTotalElements())
            .totalPages(page.getTotalPages())
            .build();
    }
}
