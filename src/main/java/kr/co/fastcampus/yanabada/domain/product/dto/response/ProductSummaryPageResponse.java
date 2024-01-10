package kr.co.fastcampus.yanabada.domain.product.dto.response;

import java.util.List;
import java.util.stream.Collectors;
import kr.co.fastcampus.yanabada.domain.product.entity.Product;
import lombok.Builder;
import org.springframework.data.domain.Page;

@Builder
public record ProductSummaryPageResponse(
    List<ProductSummaryResponse> products,
    long totalElements,
    int totalPages
) {

    public static ProductSummaryPageResponse from(Page<Product> page) {
        return ProductSummaryPageResponse.builder()
            .products(
                page.getContent()
                    .stream()
                    .map(ProductSummaryResponse::from)
                    .collect(Collectors.toList())
            )
            .totalElements(page.getTotalElements())
            .totalPages(page.getTotalPages())
            .build();
    }
}
