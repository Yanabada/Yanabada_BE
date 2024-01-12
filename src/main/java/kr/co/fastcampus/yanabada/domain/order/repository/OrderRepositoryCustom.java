package kr.co.fastcampus.yanabada.domain.order.repository;

import java.util.List;
import kr.co.fastcampus.yanabada.domain.order.entity.Order;

public interface OrderRepositoryCustom {

    List<Order> getByCheckInDateExpired();
}
