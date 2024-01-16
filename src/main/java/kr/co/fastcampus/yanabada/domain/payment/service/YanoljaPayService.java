package kr.co.fastcampus.yanabada.domain.payment.service;

import kr.co.fastcampus.yanabada.common.exception.YanoljaPayNotFoundException;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.member.repository.MemberRepository;
import kr.co.fastcampus.yanabada.domain.payment.dto.response.YanoljaPayHomeResponse;
import kr.co.fastcampus.yanabada.domain.payment.entity.YanoljaPay;
import kr.co.fastcampus.yanabada.domain.payment.repository.YanoljaPayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class YanoljaPayService {

    private final YanoljaPayRepository yanoljaPayRepository;
    private final MemberRepository memberRepository;


    public YanoljaPayHomeResponse getYanoljaPayDataByMemberId(Long memberId) {
        Member member = memberRepository.getMember(memberId);

        return getYanoljaPayData(member);
    }

    public YanoljaPayHomeResponse getYanoljaPayData(Member member) {
        YanoljaPay yanoljaPay = yanoljaPayRepository.findByMember(member)
            .orElseThrow(YanoljaPayNotFoundException::new);

        return YanoljaPayHomeResponse.from(yanoljaPay);
    }
}

//@Service
//@RequiredArgsConstructor
//public class YanoljaPayService {
//
//    private final YanoljaPayRepository yanoljaPayRepository;
//
//
//    public YanoljaPayHomeResponse getYanoljaPayData(Member member) {
//        YanoljaPay yanoljaPay = yanoljaPayRepository.findByMember(member)
//            .orElseThrow(YanoljaPayNotFoundException::new);
//
//        return YanoljaPayHomeResponse.from(yanoljaPay);
//    }
//}