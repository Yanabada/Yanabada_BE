package kr.co.fastcampus.yanabada.domain.product.dto.response;

import java.util.List;
import lombok.Builder;
import org.springframework.data.domain.Page;

@Builder
public record ProductHistoryPageResponse(
    List<ProductHistoryInfoResponse> products,
    int pageNum,
    int pageSize,
    int totalPages,
    boolean isFirst,
    boolean isLast
) {

    public static ProductHistoryPageResponse from(Page<ProductHistoryInfoResponse> responsePage) {
        return ProductHistoryPageResponse.builder()
            .products(responsePage.getContent())
            .pageNum(responsePage.getNumber())
            .pageSize(responsePage.getSize())
            .totalPages(responsePage.getTotalPages())
            .isFirst(responsePage.isFirst())
            .isLast(responsePage.isLast())
            .build();
    }
}
