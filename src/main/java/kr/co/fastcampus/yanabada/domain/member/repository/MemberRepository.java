package kr.co.fastcampus.yanabada.domain.member.repository;

import java.util.Optional;
import kr.co.fastcampus.yanabada.common.exception.MemberNotFoundException;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.member.entity.ProviderType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    default Member getMember(Long id) {
        return findById(id).orElseThrow(MemberNotFoundException::new);
    }

    default Member getMember(String email, ProviderType providerType) {
        return findMemberByEmailAndProviderType(email, providerType)
                .orElseThrow(MemberNotFoundException::new);
    }

    boolean existsByEmailAndProviderType(String email, ProviderType provider);

    boolean existsByNickName(String nickName);

    Optional<Member> findMemberByEmailAndProviderType(String email, ProviderType providerType);

}
