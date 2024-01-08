package kr.co.fastcampus.yanabada.domain.member.repository;

import kr.co.fastcampus.yanabada.common.exception.MemberNotFoundException;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    default Member getMember(Long id) {
        return findById(id).orElseThrow(MemberNotFoundException::new);
    }
}
