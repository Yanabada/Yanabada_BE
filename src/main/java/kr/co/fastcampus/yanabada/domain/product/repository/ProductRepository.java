package kr.co.fastcampus.yanabada.domain.product.repository;

import java.time.LocalDate;
import java.util.List;
import kr.co.fastcampus.yanabada.common.exception.ProductNotFoundException;
import kr.co.fastcampus.yanabada.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository
    extends JpaRepository<Product, Long>, ProductRepositoryCustom {

    @Query(
        """
        SELECT p
        FROM Product p
        JOIN FETCH p.order
        WHERE
            p.status = 'ON_SALE' AND
            p.saleEndDate <= :today
        """
    )
    List<Product> getBySaleEndDateExpired(@Param("today") LocalDate today);

    default Product getProduct(Long id) {
        return findById(id).orElseThrow(ProductNotFoundException::new);
    }
}
