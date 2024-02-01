package kr.co.fastcampus.yanabada.common.jwt.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException authException
    ) {
        sendResponse(authException);
    }

    private void sendResponse(AuthenticationException authException) {
        if (authException instanceof BadCredentialsException) {
            throw new BadCredentialsException(authException.getMessage());
        } else if (authException instanceof InternalAuthenticationServiceException) {
            throw new InsufficientAuthenticationException(authException.getMessage());
        } else if (authException instanceof InsufficientAuthenticationException) {
            throw new InsufficientAuthenticationException(authException.getMessage());
        }
    }
}


