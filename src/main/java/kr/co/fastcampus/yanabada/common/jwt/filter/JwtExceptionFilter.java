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
import kr.co.fastcampus.yanabada.common.exception.TokenNotExistAtCacheException;
import kr.co.fastcampus.yanabada.common.jwt.dto.TokenExpiredResponse;
import kr.co.fastcampus.yanabada.common.response.ResponseBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    ) throws IOException {
        /* ControllerAdvice와 같은 ExHandler 역할 수행 */

        try {
            filterChain.doFilter(request, response);
        } catch (TokenExpiredException | TokenNotExistAtCacheException e) {
            ResponseBody<TokenExpiredResponse> responseBody
                = ResponseBody.fail(e.getMessage(), new TokenExpiredResponse(true));
            completeResponse(response, e, responseBody, UNAUTHORIZED.value());
        } catch (Exception e) {
            ResponseBody<Void> responseBody
                = ResponseBody.fail(e.getMessage());
            completeResponse(response, e, responseBody, BAD_REQUEST.value());
        }
    }

    private void completeResponse(
        HttpServletResponse response,
        Exception e,
        ResponseBody responseBody,
        int status
    ) throws IOException {
        response.setStatus(status);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        String bodyString = objectMapper
            .writeValueAsString(responseBody);
        response.getWriter().write(bodyString);
    }
}
