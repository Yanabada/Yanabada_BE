package kr.co.fastcampus.yanabada.common.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import kr.co.fastcampus.yanabada.common.jwt.constant.JwtConstant;
import kr.co.fastcampus.yanabada.common.jwt.dto.TokenInfoDTO;
import kr.co.fastcampus.yanabada.common.jwt.service.TokenService;
import kr.co.fastcampus.yanabada.domain.member.entity.ProviderType;
import kr.co.fastcampus.yanabada.domain.member.entity.RoleType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final TokenService tokenService;

    @Value("${jwt.secretKey}")
    private String secretKeyPlain;
    private Key secretKey;

    @PostConstruct
    protected void init() {
        log.info("JwtProvider.init() : secretKeyPlain={}", secretKeyPlain);
        byte[] keyBytes = Decoders.BASE64URL.decode(secretKeyPlain);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenInfoDTO generateTokenInfo(String email, String role, String provider) {
        String accessToken = generateAccessToken(email, role, provider);
        String refreshToken = generateRefreshToken(email, role, provider);

        tokenService.saveRefreshToken(email, provider, refreshToken);
        return new TokenInfoDTO(accessToken, refreshToken);
    }

    public String generateAccessToken(String email, String role, String provider) {
        return generateToken(email, role, provider, JwtConstant.ACCESS_TOKEN_EXPIRE_TIME);
    }

    public String generateRefreshToken(String email, String role, String provider) {
        return generateToken(email, role, provider, JwtConstant.REFRESH_TOKEN_EXPIRE_TIME);
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
                .setClaims(claims)          // Payload를 구성하는 속성들을 정의한다.
                .setIssuedAt(now)           // 발행일자를 넣는다.
                .setExpiration(new Date(now.getTime() + tokenExpireTime))     // 토큰의 만료일시를 설정한다.
                .signWith(secretKey)        // 지정된 서명 알고리즘과 비밀 키를 사용하여 토큰을 서명한다.
                .compact();
    }

    public boolean verifyToken(String token) {
        try {
            // 토큰의 만료 시간과 현재 시간비교
            return parseClaims(token)
                    .getExpiration()
                    .after(new Date());
        } catch (Exception e) {
            return false;
        }
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
        } catch (Exception e) {
            e.printStackTrace();    //todo: CannotParseToken
            throw e;
        }
    }
}
