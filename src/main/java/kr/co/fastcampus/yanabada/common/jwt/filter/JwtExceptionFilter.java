package kr.co.fastcampus.yanabada.common.jwt.filter;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import kr.co.fastcampus.yanabada.common.exception.TokenExpiredException;
import kr.co.fastcampus.yanabada.common.response.ResponseBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        /* ControllerAdvice와 같은 ExHandler 역할 수행 */

        try {
            filterChain.doFilter(request, response);
        } catch (TokenExpiredException e) {
            completeResponse(response, e, UNAUTHORIZED.value());
        } catch (Exception e) {
            completeResponse(response, e, BAD_REQUEST.value());
        }
    }

    private void completeResponse(
        HttpServletResponse response,
        Exception e,
        int status
    ) throws IOException {
        response.setStatus(status);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        String responseBody = objectMapper
            .writeValueAsString(ResponseBody.fail(e.getMessage()));
        response.getWriter().write(responseBody);
    }
}
