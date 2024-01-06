package kr.co.fastcampus.yanabada.domain.member.service;

import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.member.entity.ProviderType;
import kr.co.fastcampus.yanabada.domain.member.entity.RoleType;
import kr.co.fastcampus.yanabada.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void saveMember() {
        Member member = Member.builder()
                .email("test@test.com")
                .memberName("test")
                .nickName("test")
                .password("1234")
                .roleType(RoleType.ROLE_USER)
                .providerType(ProviderType.EMAIL)
                .build();
        memberRepository.save(member);
    }

}
