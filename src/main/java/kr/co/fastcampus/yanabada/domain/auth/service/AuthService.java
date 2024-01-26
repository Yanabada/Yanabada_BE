package kr.co.fastcampus.yanabada.domain.auth.service;

import static kr.co.fastcampus.yanabada.domain.member.entity.ProviderType.EMAIL;
import static kr.co.fastcampus.yanabada.domain.member.entity.RoleType.ROLE_USER;

import jakarta.servlet.http.HttpServletResponse;
import java.util.Random;
import kr.co.fastcampus.yanabada.common.exception.EmailDuplicatedException;
import kr.co.fastcampus.yanabada.common.jwt.dto.TokenIssueResponse;
import kr.co.fastcampus.yanabada.common.jwt.dto.TokenRefreshResponse;
import kr.co.fastcampus.yanabada.common.jwt.service.TokenService;
import kr.co.fastcampus.yanabada.common.jwt.util.JwtProvider;
import kr.co.fastcampus.yanabada.common.utils.CookieCreator;
import kr.co.fastcampus.yanabada.domain.auth.dto.request.AuthCodeVerifyRequest;
import kr.co.fastcampus.yanabada.domain.auth.dto.request.EmailCodeSendRequest;
import kr.co.fastcampus.yanabada.domain.auth.dto.request.LoginRequest;
import kr.co.fastcampus.yanabada.domain.auth.dto.request.OauthSignUpRequest;
import kr.co.fastcampus.yanabada.domain.auth.dto.request.SignUpRequest;
import kr.co.fastcampus.yanabada.domain.auth.dto.response.AuthCodeVerifyResponse;
import kr.co.fastcampus.yanabada.domain.auth.dto.response.SignUpResponse;
import kr.co.fastcampus.yanabada.domain.member.dto.request.EmailDuplCheckRequest;
import kr.co.fastcampus.yanabada.domain.member.dto.request.NickNameDuplCheckRequest;
import kr.co.fastcampus.yanabada.domain.member.dto.response.DuplCheckResponse;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.member.entity.ProviderType;
import kr.co.fastcampus.yanabada.domain.member.repository.MemberRepository;
import kr.co.fastcampus.yanabada.domain.payment.entity.YanoljaPay;
import kr.co.fastcampus.yanabada.domain.payment.repository.YanoljaPayRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    private static final String PROFILE_AND_PNG_EXTENSION = "profile.png";
    private static final int PROFILE_IMAGE_BOUND = 5;
    private static final Random RANDOM = new Random();

    @Value("${spring.login.oauth2-password}")
    String oauthPassword;
    @Value("${s3.end-point}")
    private String s3EndPoint;

    private final MemberRepository memberRepository;
    private final YanoljaPayRepository yanoljaPayRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenService tokenService;
    private final MailAuthService mailAuthService;

    @Transactional
    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        checkEmailDupl(signUpRequest.email(), EMAIL);

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
        yanoljaPayRepository.save(YanoljaPay.create(savedMember));

        return SignUpResponse.from(savedMember.getId());
    }

    @Transactional
    public SignUpResponse oauthSignUp(OauthSignUpRequest signUpRequest) {
        checkEmailDupl(signUpRequest.email(), signUpRequest.provider());

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
        yanoljaPayRepository.save(YanoljaPay.create(savedMember));

        return SignUpResponse.from(savedMember.getId());
    }

    private void checkEmailDupl(String signUpRequest, ProviderType kakao) {
        if (memberRepository.existsByEmailAndProviderType(signUpRequest, kakao)) {
            throw new EmailDuplicatedException();
        }
    }

    private String getRandomProfileImage() {
        int randomNumber = RANDOM.nextInt(PROFILE_IMAGE_BOUND);
        return s3EndPoint + randomNumber + PROFILE_AND_PNG_EXTENSION;
    }

    @Transactional
    public void login(
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
        CookieCreator.storeLoginResponse(response, tokenIssue, member);
    }

    @Transactional
    public void loginOauth(
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
        CookieCreator.storeLoginResponse(response, tokenIssue, member);
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


    @Transactional(readOnly = true)
    public void sendAuthCodeToEmail(
        EmailCodeSendRequest emailRequest
    ) {
        boolean isExist = memberRepository
            .existsByEmailAndProviderType(emailRequest.email(), EMAIL);
        if (isExist) {
            throw new EmailDuplicatedException();
        }
        mailAuthService.sendEmail(emailRequest.email());
    }

    @Transactional(readOnly = true)
    public AuthCodeVerifyResponse verifyAuthCode(AuthCodeVerifyRequest codeRequest) {
        return new AuthCodeVerifyResponse(
            mailAuthService.verifyAuthCode(codeRequest.email(), codeRequest.code())
        );
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
