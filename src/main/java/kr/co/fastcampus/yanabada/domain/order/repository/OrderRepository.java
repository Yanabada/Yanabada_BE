package kr.co.fastcampus.yanabada.domain.order.repository;

import kr.co.fastcampus.yanabada.common.exception.OrderNotFoundException;
import kr.co.fastcampus.yanabada.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository
    extends JpaRepository<Order, Long>, OrderRepositoryCustom {

    default Order getOrder(Long id) {
        return findById(id).orElseThrow(OrderNotFoundException::new);
    }
}
