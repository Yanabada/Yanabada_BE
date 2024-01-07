package kr.co.fastcampus.yanabada.domain.auth.service;

import kr.co.fastcampus.yanabada.common.jwt.dto.TokenInfoDTO;
import kr.co.fastcampus.yanabada.common.jwt.util.JwtProvider;
import kr.co.fastcampus.yanabada.domain.auth.dto.LoginRequest;
import kr.co.fastcampus.yanabada.domain.auth.dto.SignUpRequest;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.member.entity.ProviderType;
import kr.co.fastcampus.yanabada.domain.member.entity.RoleType;
import kr.co.fastcampus.yanabada.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static kr.co.fastcampus.yanabada.domain.member.entity.ProviderType.EMAIL;
import static kr.co.fastcampus.yanabada.domain.member.entity.RoleType.ROLE_USER;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Transactional
    public Long signUp(SignUpRequest signUpRequest) {
        if(memberRepository.existsByEmailAndProviderType(signUpRequest.email(), EMAIL)) {
            throw new RuntimeException("이미 존재하는 이메일");  //todo custom + 닉네임도 중복 체크
        }

        String encodedPassword = passwordEncoder.encode(signUpRequest.password());
        Member member = Member.builder()
                .email(signUpRequest.email())
                .memberName(signUpRequest.memberName())
                .nickName(signUpRequest.nickName())
                .password(encodedPassword)
                .phoneNumber(signUpRequest.phoneNumber())
                .roleType(ROLE_USER)
                .providerType(EMAIL)
                .build();

        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }

    @Transactional
    public TokenInfoDTO login(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = loginRequest.toAuthentication();
        authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        return jwtProvider.generateTokenInfo(loginRequest.email(), ROLE_USER.name(), EMAIL.name());
    }
}
