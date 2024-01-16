package kr.co.fastcampus.yanabada.domain.product.repository;

import kr.co.fastcampus.yanabada.common.exception.ProductNotFoundException;
import kr.co.fastcampus.yanabada.domain.product.entity.Product;
import kr.co.fastcampus.yanabada.domain.product.entity.enums.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository
    extends JpaRepository<Product, Long>, ProductRepositoryCustom {

    default Product getProduct(Long id) {
        return findById(id).orElseThrow(ProductNotFoundException::new);
    }

    @Query("SELECT p FROM Product p WHERE "
        + "(p.status = :status OR :status is null) ")
    Page<Product> findByMemberId(
        Long memberId,
        @Param("status") ProductStatus status,
        Pageable pageable
    );


}
