package kr.co.fastcampus.yanabada.domain.payment.repository;

import java.util.List;
import java.util.Optional;
import kr.co.fastcampus.yanabada.common.exception.TradeNotFoundException;
import kr.co.fastcampus.yanabada.domain.payment.entity.Trade;
import kr.co.fastcampus.yanabada.domain.payment.entity.enums.TradeStatus;
import kr.co.fastcampus.yanabada.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeRepository extends JpaRepository<Trade, Long> {

    List<Trade> findByProduct(Product product);

    default Trade getTrade(Long id) {
        return findById(id).orElseThrow(TradeNotFoundException::new);
    }

    Optional<Trade> findByProductAndStatus(Product product, TradeStatus status);
}
