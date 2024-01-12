package kr.co.fastcampus.yanabada.domain.product.repository;

import java.util.List;
import kr.co.fastcampus.yanabada.domain.product.dto.request.ProductSearchRequest;
import kr.co.fastcampus.yanabada.domain.product.entity.Product;
import org.springframework.data.domain.Page;

public interface ProductRepositoryCustom {

    Page<Product> getBySearchRequest(ProductSearchRequest request);

    List<Product> getBySaleEndDateExpired();
}
