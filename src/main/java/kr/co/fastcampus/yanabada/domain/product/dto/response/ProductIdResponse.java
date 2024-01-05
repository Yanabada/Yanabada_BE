package kr.co.fastcampus.yanabada.domain.product.dto.response;

import kr.co.fastcampus.yanabada.domain.product.entity.Product;

public record ProductIdResponse(
    Long productId
) {

    public ProductIdResponse(Product product) {
        this(product.getId());
    }
}
