package kr.co.fastcampus.yanabada.domain.order.repository;

import java.time.LocalDate;
import java.util.List;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(
        """
        SELECT o
        FROM Order o
        JOIN FETCH o.room r
        JOIN FETCH r.accommodation a
        WHERE
            o.member = :member AND
            o.status = 'RESERVED' AND
            o.checkInDate >= :today
        ORDER BY o.id DESC
        """
    )
    List<Order> getSellableByMember(
        @Param("member") Member member,
        @Param("today") LocalDate today
    );
}
