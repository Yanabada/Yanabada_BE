package kr.co.fastcampus.yanabada.domain.auth.service;

import static kr.co.fastcampus.yanabada.domain.member.entity.ProviderType.EMAIL;
import static kr.co.fastcampus.yanabada.domain.member.entity.RoleType.ROLE_USER;

import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.IntStream;
import kr.co.fastcampus.yanabada.common.exception.EmailDuplicatedException;
import kr.co.fastcampus.yanabada.common.jwt.dto.TokenIssueResponse;
import kr.co.fastcampus.yanabada.common.jwt.dto.TokenRefreshResponse;
import kr.co.fastcampus.yanabada.common.jwt.service.TokenService;
import kr.co.fastcampus.yanabada.common.jwt.util.JwtProvider;
import kr.co.fastcampus.yanabada.common.utils.CookieCreator;
import kr.co.fastcampus.yanabada.common.utils.EntityCodeGenerator;
import kr.co.fastcampus.yanabada.common.utils.RandomNumberGenerator;
import kr.co.fastcampus.yanabada.domain.accommodation.entity.Room;
import kr.co.fastcampus.yanabada.domain.accommodation.repository.RoomRepository;
import kr.co.fastcampus.yanabada.domain.auth.dto.request.LoginRequest;
import kr.co.fastcampus.yanabada.domain.auth.dto.request.OauthSignUpRequest;
import kr.co.fastcampus.yanabada.domain.auth.dto.request.SignUpRequest;
import kr.co.fastcampus.yanabada.domain.auth.dto.response.SignUpResponse;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.member.entity.ProviderType;
import kr.co.fastcampus.yanabada.domain.member.repository.MemberRepository;
import kr.co.fastcampus.yanabada.domain.order.entity.Order;
import kr.co.fastcampus.yanabada.domain.order.entity.enums.OrderStatus;
import kr.co.fastcampus.yanabada.domain.order.entity.enums.PaymentType;
import kr.co.fastcampus.yanabada.domain.order.repository.OrderRepository;
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

    private static final int PROFILE_IMAGE_BOUND = 5;
    private static final int PROFILE_IMAGE_ORIGIN = 0;
    private static final int ROOM_ID_BOUND = 101;
    private static final int ROOM_ID_ORIGIN = 1;
    private static final String DUMMY_PERSON_NAME = "홍길동";
    private static final String DUMMY_PERSON_PHONE_NUMBER = "010-1234-1234";
    private static final String PROFILE_AND_PNG_EXTENSION = "profile.png";

    @Value("${spring.login.oauth2-password}")
    String oauthPassword;
    @Value("${spring.cookie.secure}")
    boolean secure;
    @Value("${spring.cookie.domain}")
    String domain;
    @Value("${s3.end-point}")
    private String s3EndPoint;

    private final MemberRepository memberRepository;
    private final YanoljaPayRepository yanoljaPayRepository;
    private final RoomRepository roomRepository;
    private final OrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenService tokenService;

    @Transactional
    public SignUpResponse signUp(SignUpRequest signUpRequest) {
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
        yanoljaPayRepository.save(YanoljaPay.create(savedMember));
        IntStream.range(0, 2).forEach(i -> orderRepository.save(createRandomDummyOrder (member)));

        return SignUpResponse.from(savedMember.getId());
    }

    @Transactional
    public SignUpResponse oauthSignUp(OauthSignUpRequest signUpRequest) {

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
        IntStream.range(0, 2).forEach(i -> orderRepository.save(createRandomDummyOrder (member)));

        return SignUpResponse.from(savedMember.getId());
    }

    private String getRandomProfileImage() {
        int randomNumber =
            RandomNumberGenerator.generate(PROFILE_IMAGE_ORIGIN, PROFILE_IMAGE_BOUND);
        return s3EndPoint + randomNumber + PROFILE_AND_PNG_EXTENSION;
    }

    private Order createRandomDummyOrder (Member member) {
        int randomRoomId = RandomNumberGenerator.generate(ROOM_ID_ORIGIN, ROOM_ID_BOUND);
        Room randomRoom = roomRepository.getRoom((long) randomRoomId);
        LocalDate checkInDate = LocalDate.now().plusDays(4);
        return Order.create(
            randomRoom,
            member,
            checkInDate,
            checkInDate.plusDays(1),
            OrderStatus.RESERVED,
            randomRoom.getPrice(),
            DUMMY_PERSON_NAME,
            DUMMY_PERSON_PHONE_NUMBER,
            DUMMY_PERSON_NAME,
            DUMMY_PERSON_PHONE_NUMBER,
            LocalDateTime.now(),
            PaymentType.CREDIT,
            EntityCodeGenerator.generate()
        );
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
}
