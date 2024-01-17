package kr.co.fastcampus.yanabada.domain.payment.service;

import kr.co.fastcampus.yanabada.common.exception.MemberNotFoundException;
import kr.co.fastcampus.yanabada.common.exception.PasswordConfirmationException;
import kr.co.fastcampus.yanabada.common.exception.YanoljaPayNotFoundException;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.member.repository.MemberRepository;
import kr.co.fastcampus.yanabada.domain.payment.dto.request.PayPasswordRequest;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.YanoljaPayHomeResponse;
import kr.co.fastcampus.yanabada.domain.payment.entity.YanoljaPay;
import kr.co.fastcampus.yanabada.domain.payment.repository.YanoljaPayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class YanoljaPayService {

    private final YanoljaPayRepository yanoljaPayRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public YanoljaPayHomeResponse getYanoljaPay(Long memberId) {
        Member member = memberRepository.getMember(memberId);

        return getYanoljaPay(member);
    }

    private YanoljaPayHomeResponse getYanoljaPay(Member member) {
        YanoljaPay yanoljaPay = yanoljaPayRepository.findByMember(member)
            .orElseThrow(YanoljaPayNotFoundException::new);

        return YanoljaPayHomeResponse.from(yanoljaPay);
    }

    @Transactional
    public void setPayPassword(Long memberId, PayPasswordRequest payPasswordRequest) {
        if (!payPasswordRequest.isPasswordMatch()) {
            throw new PasswordConfirmationException();
        }

        Member member = memberRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::new);

        yanoljaPayRepository.updateSimplePassword(member, payPasswordRequest.password());
    }
}