package kr.co.fastcampus.yanabada.domain.payment.repository;

import java.util.List;
import kr.co.fastcampus.yanabada.common.exception.TradeNotFoundException;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.payment.entity.Trade;
import kr.co.fastcampus.yanabada.domain.payment.entity.enums.TradeRole;
import kr.co.fastcampus.yanabada.domain.payment.entity.enums.TradeStatus;
import kr.co.fastcampus.yanabada.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TradeRepository extends JpaRepository<Trade, Long> {

    List<Trade> findByProduct(Product product);

    default Trade getTrade(Long id) {
        return findById(id).orElseThrow(TradeNotFoundException::new);
    }

    @Query("SELECT t FROM Trade t "
        + "WHERE (:role = 'SELLER' AND t.seller = :member OR "
        + ":role = 'BUYER' AND t.buyer = :member) "
        + "AND (:status IS NULL OR t.status = :status)")
    Page<Trade> findByMemberRoleAndStatus(
        @Param("member") Member member,
        @Param("role") String role,
        @Param("status")TradeStatus status,
        Pageable pageable
    );


}
