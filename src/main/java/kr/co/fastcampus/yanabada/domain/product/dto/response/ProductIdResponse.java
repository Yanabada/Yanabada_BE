package kr.co.fastcampus.yanabada.domain.product.dto.response;

import kr.co.fastcampus.yanabada.domain.product.entity.Product;
import lombok.Builder;

@Builder
public record ProductIdResponse(
    Long productId
) {

    public static ProductIdResponse from(Product product) {
        return ProductIdResponse.builder()
            .productId(product.getId())
            .build();
    }
}
