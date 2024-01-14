package kr.co.fastcampus.yanabada.domain.payment.service;

import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.member.repository.MemberRepository;
import kr.co.fastcampus.yanabada.domain.payment.dto.request.OpenBankingConsentRequest;
import org.springframework.stereotype.Service;

@Service
public class OpenBankingService {

    private final MemberRepository memberRepository;

    public OpenBankingService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member getOpenBankingInfo(Long memberId) {
        // 오픈뱅킹 정보 조회 로직
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    public void processConsent(Long memberId, OpenBankingConsentRequest consentRequest) {
        if (!consentRequest.consent()) {
            throw new IllegalArgumentException("오픈뱅킹 서비스 이용을 위한 동의가 필요합니다.");
        }
    }
}

