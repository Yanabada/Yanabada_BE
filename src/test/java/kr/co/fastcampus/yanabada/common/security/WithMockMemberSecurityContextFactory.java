package kr.co.fastcampus.yanabada.common.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockMemberSecurityContextFactory
    implements WithSecurityContextFactory<WithMockMember> {

    @Override
    public SecurityContext createSecurityContext(WithMockMember annotation) {
        PrincipalDetails memberDetails = PrincipalDetails.builder()
            .id(annotation.id())
            .email("test@email.com")
            .build();
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(
            new UsernamePasswordAuthenticationToken(
                memberDetails,
                null,
                memberDetails.getAuthorities())
        );
        return securityContext;
    }
}
