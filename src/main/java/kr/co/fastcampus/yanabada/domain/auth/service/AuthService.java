package kr.co.fastcampus.yanabada.domain.auth.service;

import static kr.co.fastcampus.yanabada.domain.member.entity.ProviderType.EMAIL;
import static kr.co.fastcampus.yanabada.domain.member.entity.RoleType.ROLE_USER;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Random;
import kr.co.fastcampus.yanabada.common.exception.EmailDuplicatedException;
import kr.co.fastcampus.yanabada.common.exception.JsonProcessFailedException;
import kr.co.fastcampus.yanabada.common.jwt.dto.TokenIssueResponse;
import kr.co.fastcampus.yanabada.common.jwt.dto.TokenRefreshResponse;
import kr.co.fastcampus.yanabada.common.jwt.service.TokenService;
import kr.co.fastcampus.yanabada.common.jwt.util.JwtProvider;
import kr.co.fastcampus.yanabada.domain.auth.dto.request.LoginRequest;
import kr.co.fastcampus.yanabada.domain.auth.dto.request.OauthSignUpRequest;
import kr.co.fastcampus.yanabada.domain.auth.dto.request.SignUpRequest;
import kr.co.fastcampus.yanabada.domain.auth.dto.response.LoginResponse;
import kr.co.fastcampus.yanabada.domain.member.dto.response.MemberDetailResponse;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.member.entity.ProviderType;
import kr.co.fastcampus.yanabada.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Service
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenService tokenService;
    private final ObjectMapper objectMapper;
    @Value("${spring.login.oauth2-password}")
    String oauthPassword;

    @Transactional
    public Long signUp(SignUpRequest signUpRequest) {
        if (memberRepository.existsByEmailAndProviderType(signUpRequest.email(), EMAIL)) {
            throw new EmailDuplicatedException();
        }

        String encodedPassword = passwordEncoder.encode(signUpRequest.password());

        Member member = Member.builder()
            .email(signUpRequest.email())
            .nickName(signUpRequest.nickName())
            .password(encodedPassword)
            .phoneNumber(signUpRequest.phoneNumber())
            .roleType(ROLE_USER)
            .image(getRandomProfileImage())
            .providerType(EMAIL)
            .build();

        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }

    @Transactional
    public Long oauthSignUp(OauthSignUpRequest signUpRequest) {

        String encodedPassword = passwordEncoder.encode(oauthPassword);
        Member member = Member.builder()
            .email(signUpRequest.email())
            .nickName(signUpRequest.nickName())
            .password(encodedPassword)
            .phoneNumber(signUpRequest.phoneNumber())
            .roleType(ROLE_USER)
            .image(getRandomProfileImage())
            .providerType(signUpRequest.provider())
            .build();

        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }

    private String getRandomProfileImage() {
        Random random = new Random();
        int randomNumber = random.nextInt(5) + 1;
        return randomNumber + "profile.png";     //todo: 환경 변수 분리
    }

    @Transactional
    public LoginResponse login(
        HttpServletResponse response, LoginRequest loginRequest
    ) {
        UsernamePasswordAuthenticationToken authenticationToken = loginRequest.toAuthentication();
        authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenIssueResponse tokenIssue
            = tokenService.getTokenIssue(loginRequest.email(), EMAIL.name());
        if (tokenIssue == null) {
            tokenIssue = jwtProvider
                .generateTokenInfo(loginRequest.email(), ROLE_USER.name(), EMAIL.name());
        }
        Member member = memberRepository.getMember(loginRequest.email(), EMAIL);
        setTokenInCookie(response, tokenIssue, member);
        return LoginResponse.from(tokenIssue, member);
    }

    @Transactional
    public LoginResponse loginOauth(
        HttpServletResponse response,
        LoginRequest loginRequest,
        ProviderType providerType
    ) {
        TokenIssueResponse tokenIssue
            = tokenService.getTokenIssue(loginRequest.email(), providerType.name());
        if (tokenIssue == null) {
            tokenIssue = jwtProvider
                .generateTokenInfo(loginRequest.email(), ROLE_USER.name(), providerType.name());
        }
        Member member = memberRepository.getMember(loginRequest.email(), providerType);
        setTokenInCookie(response, tokenIssue, member);
        return LoginResponse.from(tokenIssue, member);
    }

    private void setTokenInCookie(
        HttpServletResponse response,
        TokenIssueResponse tokenIssue,
        Member member
    ) {
        setValueInCookie(response, "accessToken", tokenIssue.accessToken());
        setValueInCookie(response, "refreshToken", tokenIssue.refreshToken());
        setValueInCookie(response, "id", String.valueOf(member.getId()));
        setValueInCookie(response, "email", String.valueOf(member.getEmail()));
        setValueInCookie(response, "nickName", String.valueOf(member.getNickName()));
        setValueInCookie(response, "image", String.valueOf(member.getImage()));
        setValueInCookie(response, "provider", String.valueOf(member.getProviderType()));
    }

    private void setValueInCookie(
        HttpServletResponse response, String key, String value
    ) {
        ResponseCookie cookie = ResponseCookie
            .from(key, value)
            .httpOnly(true)
            .secure(true)
            .path("/")
            .sameSite("None")
            .build();   //todo: domain 서브도메인 맞추기
        response.addHeader("Set-Cookie", cookie.toString());
    }

    private String getMemberDtoJsonStr(Member member) {
        try {
            MemberDetailResponse memberDto = MemberDetailResponse.from(member);
            return objectMapper.writeValueAsString(memberDto);
        } catch (JsonProcessingException e) {
            throw new JsonProcessFailedException();
        }
    }

    @Transactional
    public void logout(String refreshToken) {
        String email = jwtProvider.getEmail(refreshToken);
        String provider = jwtProvider.getProvider(refreshToken);
        tokenService.deleteToken(email, provider);
    }

    @Transactional
    public TokenRefreshResponse generateNewAccessToken(String refreshToken) {
        String email = jwtProvider.getEmail(refreshToken);
        String role = jwtProvider.getRole(refreshToken);
        String provider = jwtProvider.getProvider(refreshToken);
        String newAccessToken = jwtProvider.generateAccessToken(email, role, provider);
        tokenService.updateAccessToken(email, provider, newAccessToken);
        return new TokenRefreshResponse(newAccessToken);
    }
}
