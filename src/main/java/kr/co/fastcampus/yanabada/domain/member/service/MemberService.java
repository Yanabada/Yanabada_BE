package kr.co.fastcampus.yanabada.domain.member.service;

import kr.co.fastcampus.yanabada.domain.member.dto.request.FcmTokenUpdateRequest;
import kr.co.fastcampus.yanabada.domain.member.dto.request.NickNameModifyRequest;
import kr.co.fastcampus.yanabada.domain.member.dto.request.PasswordModifyRequest;
import kr.co.fastcampus.yanabada.domain.member.dto.request.PhoneNumberModifyRequest;
import kr.co.fastcampus.yanabada.domain.member.dto.response.MemberDetailResponse;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.member.entity.ProviderType;
import kr.co.fastcampus.yanabada.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public MemberDetailResponse findMember(String email, ProviderType providerType) {
        return MemberDetailResponse.from(memberRepository.getMember(email, providerType));
    }

    @Transactional
    public void modifyPassword(
        PasswordModifyRequest passwordRequest,
        String email,
        ProviderType providerType
    ) {
        Member member = memberRepository.getMember(email, providerType);
        member.updatePassword(passwordEncoder.encode(passwordRequest.password()));
    }

    @Transactional
    public void modifyNickName(
        NickNameModifyRequest nickNameRequest,
        String email,
        ProviderType providerType
    ) {
        Member member = memberRepository.getMember(email, providerType);
        member.updateNickName(nickNameRequest.nickName());
    }

    @Transactional
    public void modifyPhoneNumber(
        PhoneNumberModifyRequest phoneNumberRequest,
        String email,
        ProviderType providerType
    ) {
        Member member = memberRepository.getMember(email, providerType);
        member.updatePhoneNumber(phoneNumberRequest.phoneNumber());
    }

    @Transactional
    public void updateFcmToken(
        Long memberId, FcmTokenUpdateRequest fcmTokenRequest
    ) {
        Member member = memberRepository.getMember(memberId);
        member.updateFcmToken(fcmTokenRequest.fcmToken());
    }

}
