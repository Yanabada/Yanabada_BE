package kr.co.fastcampus.yanabada.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import kr.co.fastcampus.yanabada.domain.member.entity.ProviderType;
import kr.co.fastcampus.yanabada.domain.member.entity.RoleType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.management.relation.Role;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

//    private final RefreshTokenService tokenService;
    @Value("${jwt.secretKey}")
    private String secretKeyPlain;
    private Key secretKey;

    @PostConstruct
    protected void init() {
        log.info("JwtProvider.init() : secretKeyPlain={}", secretKeyPlain);
        byte[] keyBytes = Decoders.BASE64URL.decode(secretKeyPlain);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenInfoDTO generateTokenInfo(String email, RoleType role, ProviderType provider) {
        String accessToken = generateAccessToken(email, role, provider);
        String refreshToken = generateRefreshToken(email, role, provider);

//        tokenService.saveTokenInfo(email, refreshToken, accessToken); todo: 토큰을 redis에 저장
        return new TokenInfoDTO(accessToken, refreshToken);
    }

    private String generateAccessToken(String email, RoleType role, ProviderType provider) {
        return generateToken(email, role, provider, JwtConstant.ACCESS_TOKEN_EXPIRE_TIME);
    }

    private String generateRefreshToken(String email, RoleType role, ProviderType provider) {
        return generateToken(email, role, provider, JwtConstant.REFRESH_TOKEN_EXPIRE_TIME);
    }

    private String generateToken(
            String email, RoleType role,
            ProviderType provider, long tokenExpireTime
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
            Jws<Claims> claims =
                    Jwts.parserBuilder().setSigningKey(secretKey)
                    .build().parseClaimsJws(token);
            // 토큰의 만료 시간과 현재 시간비교
            return claims.getBody()
                    .getExpiration()
                    .after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String getEmail(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey)
                .build().parseClaimsJws(token).getBody().getSubject();
    }

    public RoleType getRole(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey)
                .build().parseClaimsJws(token).getBody().get("role", RoleType.class);
    }

    public ProviderType getProvider(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey)
                .build().parseClaimsJws(token).getBody().get("role", ProviderType.class);
    }
}
