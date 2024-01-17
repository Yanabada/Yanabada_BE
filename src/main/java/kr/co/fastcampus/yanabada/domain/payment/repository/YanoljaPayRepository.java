package kr.co.fastcampus.yanabada.domain.payment.repository;

import java.util.Optional;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.payment.entity.YanoljaPay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YanoljaPayRepository extends JpaRepository<YanoljaPay, Long> {
    Optional<YanoljaPay> findByMember(Member member);
}

