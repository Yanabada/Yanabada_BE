package kr.co.fastcampus.yanabada.domain.member.service;

import static kr.co.fastcampus.yanabada.common.utils.TestUtils.createMember;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import kr.co.fastcampus.yanabada.domain.member.dto.response.MemberDetailResponse;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.member.entity.ProviderType;
import kr.co.fastcampus.yanabada.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @DisplayName("정상적인 조회일 경우, 멤버 프로필 상세 조회를 반환합니다.")
    @Test
    void findMember_success() {
        //given
        Member member = createMember();
        given(memberRepository.getMember(anyString(), any(ProviderType.class)))
            .willReturn(member);

        //when
        MemberDetailResponse result
            = memberService.findMember(member.getEmail(), member.getProviderType());

        //then
        assertThat(result).isNotNull();
    }


}