package kr.co.fastcampus.yanabada.domain.order.repository;

import kr.co.fastcampus.yanabada.domain.order.entity.RoomOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<RoomOrder, Long> {
}
