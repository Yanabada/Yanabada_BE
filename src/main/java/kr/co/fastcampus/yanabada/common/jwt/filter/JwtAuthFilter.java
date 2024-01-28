package kr.co.fastcampus.yanabada.common.jwt.filter;

import static kr.co.fastcampus.yanabada.common.jwt.constant.JwtConstant.AUTHORIZATION_HEADER;
import static kr.co.fastcampus.yanabada.common.jwt.constant.JwtConstant.BEARER_PREFIX;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import kr.co.fastcampus.yanabada.common.exception.MemberNotFoundException;
import kr.co.fastcampus.yanabada.common.exception.TokenNotExistAtCacheException;
import kr.co.fastcampus.yanabada.common.exception.TokenNotValidatedException;
import kr.co.fastcampus.yanabada.common.jwt.service.TokenService;
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

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;
    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String token = extractTokenFromRequest(request);

        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        String email = jwtProvider.getEmail(token);
        String provider = jwtProvider.getProvider(token);

        if (!tokenService.isExistToken(email, provider)) {
            /* 로그아웃 된 토큰 사용 */
            throw new TokenNotExistAtCacheException();
        }

        try {
            Member findMember = memberRepository
                .getMember(email, ProviderType.valueOf(provider));
            PrincipalDetails principalDetails = PrincipalDetails.of(findMember);

            // SecurityContext에 인증 객체를 등록
            Authentication auth = getAuthentication(principalDetails);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (MemberNotFoundException e) {
            throw new TokenNotValidatedException();
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
            return token.substring(BEARER_PREFIX.length());
        }
        return null;
    }
    
}
