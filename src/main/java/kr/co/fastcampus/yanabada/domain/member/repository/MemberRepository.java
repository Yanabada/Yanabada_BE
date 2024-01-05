package kr.co.fastcampus.yanabada.domain.member.repository;

import kr.co.fastcampus.yanabada.common.exception.MemberNotFoundException;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.member.entity.ProviderType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    default Member getMember(Long memberId) {
        return findById(memberId).orElseThrow(MemberNotFoundException::new);
    }

    default Member getMember(String email, ProviderType providerType) {
        return findMemberByEmailAndProviderType(email, providerType)
                .orElseThrow(MemberNotFoundException::new);
    }

    Optional<Member> findMemberByEmailAndProviderType(String email, ProviderType providerType);


}
