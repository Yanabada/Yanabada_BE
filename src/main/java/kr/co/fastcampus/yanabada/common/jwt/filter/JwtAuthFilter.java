package kr.co.fastcampus.yanabada.common.jwt.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.fastcampus.yanabada.common.exception.MemberNotFoundException;
import kr.co.fastcampus.yanabada.common.jwt.util.JwtProvider;
import kr.co.fastcampus.yanabada.common.security.PrincipalDetails;
import kr.co.fastcampus.yanabada.domain.member.entity.Member;
import kr.co.fastcampus.yanabada.domain.member.entity.ProviderType;
import kr.co.fastcampus.yanabada.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static kr.co.fastcampus.yanabada.common.jwt.constant.JwtConstant.AUTHORIZATION_HEADER;
import static kr.co.fastcampus.yanabada.common.jwt.constant.JwtConstant.BEARER_PREFIX;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        /* 토큰 재발급, 로그아웃일 경우 해당 필터 실행 안됨 */
        return request.getRequestURI().contains("token/");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String token = extractTokenFromRequest(request);

        // 토큰 검사 생략(모두 허용 URL의 경우 토큰 검사 통과)
        if (!StringUtils.hasText(token)) {
            doFilter(request, response, filterChain);
            return;
        }

        // AccessToken을 검증하고, 만료되었을경우 예외를 발생시킨다.
        if (!jwtProvider.verifyToken(token)) {
            throw new RuntimeException("Access Token 만료!"); //todo: Custom Ex
        }

        try {
            String email = jwtProvider.getEmail(token);
            ProviderType provider = ProviderType.valueOf(jwtProvider.getProvider(token));

            Member findMember = memberRepository.getMember(email, provider);

            PrincipalDetails principalDetails = PrincipalDetails.of(findMember);

            // SecurityContext에 인증 객체를 등록해준다.
            Authentication auth = getAuthentication(principalDetails);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch(MemberNotFoundException e) {
            throw new RuntimeException("Illegal Token");   //todo
        }

        filterChain.doFilter(request, response);
    }

    public Authentication getAuthentication(PrincipalDetails principal) {
        return new UsernamePasswordAuthenticationToken(
                principal, "", principal.getAuthorities()
        );
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX)) {
            return token.substring(7);
        }
        return null;
    }
}
