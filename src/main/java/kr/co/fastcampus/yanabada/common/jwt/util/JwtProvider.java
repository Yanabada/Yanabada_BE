package kr.co.fastcampus.yanabada.common.jwt.util;

import static kr.co.fastcampus.yanabada.common.jwt.constant.JwtConstant.ACCESS_TOKEN_EXPIRE_TIME;
import static kr.co.fastcampus.yanabada.common.jwt.constant.JwtConstant.REFRESH_TOKEN_EXPIRE_TIME;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import kr.co.fastcampus.yanabada.common.exception.ClaimParseFailedException;
import kr.co.fastcampus.yanabada.common.exception.TokenExpiredException;
import kr.co.fastcampus.yanabada.common.jwt.dto.TokenIssueResponse;
import kr.co.fastcampus.yanabada.common.jwt.service.TokenService;
import kr.co.fastcampus.yanabada.common.security.PrincipalDetails;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.member.entity.ProviderType;
import kr.co.fastcampus.yanabada.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final TokenService tokenService;
    private final MemberRepository memberRepository;

    @Value("${jwt.secretKey}")
    private String secretKeyPlain;
    private Key secretKey;

    @PostConstruct
    protected void init() {
        byte[] keyBytes = Decoders.BASE64URL.decode(secretKeyPlain);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenIssueResponse generateTokenInfo(String email, String role, String provider) {
        String accessToken = generateAccessToken(email, role, provider);
        String refreshToken = generateRefreshToken(email, role, provider);
        TokenIssueResponse tokenIssue = new TokenIssueResponse(accessToken, refreshToken);
        tokenService.saveTokenIssue(email, provider, tokenIssue);
        return tokenIssue;
    }

    public String generateAccessToken(String email, String role, String provider) {
        return generateToken(email, role, provider, ACCESS_TOKEN_EXPIRE_TIME);
    }

    public String generateRefreshToken(String email, String role, String provider) {
        return generateToken(email, role, provider, REFRESH_TOKEN_EXPIRE_TIME);
    }

    private String generateToken(
            String email, String role,
            String provider, long tokenExpireTime
    ) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("role", role);
        claims.put("provider", provider);

        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)          // Payload 설정
                .setIssuedAt(now)           // 발행일자 설정
                .setExpiration(new Date(now.getTime() + tokenExpireTime))     // 토큰 만료일짜 설정
                .signWith(secretKey)        // 비밀 키로 토큰 서명
                .compact();
    }

    public boolean verifyToken(String token) {
        return parseClaims(token)
                .getExpiration()
                .after(new Date());
    }

    public String getEmail(String token) {
        return parseClaims(token).getSubject();
    }

    public String getRole(String token) {
        return parseClaims(token).get("role", String.class);
    }

    public String getProvider(String token) {
        return parseClaims(token).get("provider", String.class);
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(secretKey)
                    .build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException();
        } catch (Exception e) {
            throw new ClaimParseFailedException();
        }
    }

    public boolean isLoggedOut(String email, String provider) {
        return !tokenService.isExistToken(email, provider);
    }

    public void saveAuthInContextHolder(
        String email, ProviderType providerType
    ) {
        Member findMember = memberRepository
            .getMember(email, providerType);
        PrincipalDetails principalDetails = PrincipalDetails.of(findMember);

        // SecurityContext에 인증 객체를 등록
        Authentication auth = getAuthentication(principalDetails);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private Authentication getAuthentication(PrincipalDetails principal) {
        return new UsernamePasswordAuthenticationToken(
            principal, "", principal.getAuthorities()
        );
    }
}
