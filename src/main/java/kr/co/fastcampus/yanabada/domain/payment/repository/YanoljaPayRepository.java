package kr.co.fastcampus.yanabada.domain.payment.repository;

import io.lettuce.core.dynamic.annotation.Param;
import jakarta.validation.constraints.Pattern;
import java.util.Optional;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.payment.entity.YanoljaPay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface YanoljaPayRepository extends JpaRepository<YanoljaPay, Long> {

    Optional<YanoljaPay> findByMember(Member member);

    @Modifying
    @Query("UPDATE YanoljaPay y SET y.simplePassword = :password WHERE y.member = :member")
    void updateSimplePassword(@Param("member") Member member, @Param("password") String password);
}

