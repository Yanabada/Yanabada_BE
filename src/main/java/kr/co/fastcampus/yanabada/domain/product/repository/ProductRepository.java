package kr.co.fastcampus.yanabada.domain.product.repository;

import kr.co.fastcampus.yanabada.common.exception.ProductNotFoundException;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
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

    @Query("SELECT p FROM Product p "
        + "JOIN FETCH p.order o "
        + "JOIN FETCH o.member m "
        + "LEFT JOIN Trade t ON t.product = p "
        + "WHERE m = :member "
        + "AND (:status IS NULL OR p.status = :status) "
        + "AND p.status != 'CANCELED' "
        + "AND (p.status != 'SOLD_OUT' OR (p.status = 'SOLD_OUT' AND t.hasSellerDeleted = false))")
    Page<Product> findProductsByMemberAndStatus(
        @Param("member") Member member,
        @Param("status") ProductStatus status,
        Pageable pageable
    );
}
