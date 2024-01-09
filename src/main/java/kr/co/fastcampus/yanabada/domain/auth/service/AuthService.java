package kr.co.fastcampus.yanabada.domain.auth.service;

import static kr.co.fastcampus.yanabada.domain.member.entity.ProviderType.EMAIL;
import static kr.co.fastcampus.yanabada.domain.member.entity.RoleType.ROLE_USER;

import kr.co.fastcampus.yanabada.common.jwt.dto.TokenIssueResponse;
import kr.co.fastcampus.yanabada.common.jwt.service.TokenService;
import kr.co.fastcampus.yanabada.common.jwt.util.JwtProvider;
import kr.co.fastcampus.yanabada.domain.auth.dto.LoginRequest;
import kr.co.fastcampus.yanabada.domain.auth.dto.SignUpRequest;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenService tokenService;

    @Transactional
    public Long signUp(SignUpRequest signUpRequest) {
        if (memberRepository.existsByEmailAndProviderType(signUpRequest.email(), EMAIL)) {
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
    public TokenIssueResponse login(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = loginRequest.toAuthentication();  //todo: BadCredentialException을 새로운 커스텀 Ex에 감싸서 보낼지 고민
        authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        return jwtProvider.generateTokenInfo(loginRequest.email(), ROLE_USER.name(), EMAIL.name());
    }

    @Transactional
    public void logout(String refreshToken) {
        String email = jwtProvider.getEmail(refreshToken);
        String provider = jwtProvider.getProvider(refreshToken);
        tokenService.deleteToken(email, provider);
    }

    @Transactional
    public void generateNewAccessToken(String refreshToken) {
        String email = jwtProvider.getEmail(refreshToken);
        String provider = jwtProvider.getProvider(refreshToken);
        TokenIssueResponse tokenIssue
            = tokenService.getTokenIssue(email, provider);
    }
}
