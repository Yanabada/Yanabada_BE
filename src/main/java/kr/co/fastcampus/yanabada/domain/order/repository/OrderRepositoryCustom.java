package kr.co.fastcampus.yanabada.domain.order.repository;

import java.util.List;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.order.entity.Order;

public interface OrderRepositoryCustom {

    List<Order> getSellableByMember(Member member);

    List<Order> getByCheckInDateExpired();
}
