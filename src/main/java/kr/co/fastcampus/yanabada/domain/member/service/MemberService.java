package kr.co.fastcampus.yanabada.domain.member.service;

import static kr.co.fastcampus.yanabada.domain.member.entity.ProviderType.EMAIL;

import kr.co.fastcampus.yanabada.domain.member.dto.request.EmailDuplCheckRequest;
import kr.co.fastcampus.yanabada.domain.member.dto.request.ImgUrlChangeRequest;
import kr.co.fastcampus.yanabada.domain.member.dto.request.NickNameChangeRequest;
import kr.co.fastcampus.yanabada.domain.member.dto.request.NickNameDuplCheckRequest;
import kr.co.fastcampus.yanabada.domain.member.dto.request.PasswordChangeRequest;
import kr.co.fastcampus.yanabada.domain.member.dto.request.PhoneNumberChangeRequest;
import kr.co.fastcampus.yanabada.domain.member.dto.response.DuplCheckResponse;
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

    @Transactional
    public void modifyPassword(
        PasswordChangeRequest passwordRequest,
        String email,
        ProviderType providerType
    ) {
        Member member = memberRepository.getMember(email, providerType);
        member.updatePassword(passwordEncoder.encode(passwordRequest.password()));
    }

    @Transactional
    public void modifyNickName(
        NickNameChangeRequest nickNameRequest,
        String email,
        ProviderType providerType
    ) {
        Member member = memberRepository.getMember(email, providerType);
        member.updateNickName(nickNameRequest.nickName());
    }

    @Transactional
    public void modifyPhoneNumber(
        PhoneNumberChangeRequest phoneNumberRequest,
        String email,
        ProviderType providerType
    ) {
        Member member = memberRepository.getMember(email, providerType);
        member.updatePhoneNumber(phoneNumberRequest.phoneNumber());
    }

    @Transactional
    public void modifyImageUrl(
        ImgUrlChangeRequest imgUrlRequest,
        String email,
        ProviderType providerType
    ) {
        Member member = memberRepository.getMember(email, providerType);
        log.info("email={}", member.getEmail());
        member.updateImageUrl(imgUrlRequest.imageUrl());
        log.info("imgUrl={}", member.getImageUrl());
    }

    @Transactional(readOnly = true)
    public DuplCheckResponse isExistEmail(
        EmailDuplCheckRequest emailRequest
    ) {
        boolean isExist = memberRepository
            .existsByEmailAndProviderType(emailRequest.email(), EMAIL);
        return new DuplCheckResponse(isExist);
    }

    @Transactional(readOnly = true)
    public DuplCheckResponse isExistNickName(
        NickNameDuplCheckRequest nickNameRequest
    ) {
        boolean isExist = memberRepository.existsByNickName(nickNameRequest.nickName());
        return new DuplCheckResponse(isExist);
    }

}
