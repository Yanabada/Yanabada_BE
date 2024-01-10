package kr.co.fastcampus.yanabada.domain.product.repository;

import kr.co.fastcampus.yanabada.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository
    extends JpaRepository<Product, Long>, ProductRepositoryCustom {

}
