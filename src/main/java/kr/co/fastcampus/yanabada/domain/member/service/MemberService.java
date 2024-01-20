package kr.co.fastcampus.yanabada.domain.member.service;

import static kr.co.fastcampus.yanabada.domain.member.entity.ProviderType.EMAIL;

import kr.co.fastcampus.yanabada.common.exception.EmailDuplicatedException;
import kr.co.fastcampus.yanabada.domain.auth.dto.request.EmailAuthRequest;
import kr.co.fastcampus.yanabada.domain.auth.dto.response.EmailAuthResponse;
import kr.co.fastcampus.yanabada.domain.auth.service.MailAuthService;
import kr.co.fastcampus.yanabada.domain.member.dto.request.FcmTokenUpdateRequest;
import kr.co.fastcampus.yanabada.domain.member.dto.request.ImageModifyRequest;
import kr.co.fastcampus.yanabada.domain.member.dto.request.NickNameDuplCheckRequest;
import kr.co.fastcampus.yanabada.domain.member.dto.request.NickNameModifyRequest;
import kr.co.fastcampus.yanabada.domain.member.dto.request.PasswordModifyRequest;
import kr.co.fastcampus.yanabada.domain.member.dto.request.PhoneNumberModifyRequest;
import kr.co.fastcampus.yanabada.domain.member.dto.response.DuplCheckResponse;
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
    private final MailAuthService mailAuthService;

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
    public void modifyImage(
        ImageModifyRequest imageRequest,
        String email,
        ProviderType providerType
    ) {
        Member member = memberRepository.getMember(email, providerType);
        member.updateImage(imageRequest.image());
    }

    @Transactional(readOnly = true)
    public EmailAuthResponse verifyEmail(
        EmailAuthRequest emailRequest
    ) {
        boolean isExist = memberRepository
            .existsByEmailAndProviderType(emailRequest.email(), EMAIL);
        if (isExist) {
            throw new EmailDuplicatedException();
        }
        return new EmailAuthResponse(mailAuthService.sendEmail(emailRequest.email()));
    }

    @Transactional(readOnly = true)
    public DuplCheckResponse isExistNickName(
        NickNameDuplCheckRequest nickNameRequest
    ) {
        boolean isExist = memberRepository.existsByNickName(nickNameRequest.nickName());
        return new DuplCheckResponse(isExist);
    }

    @Transactional
    public void updateFcmToken(
        Long memberId, FcmTokenUpdateRequest fcmTokenRequest
    ) {
        Member member = memberRepository.getMember(memberId);
        member.updateFcmToken(fcmTokenRequest.fcmToken());
    }

}
