package kr.co.fastcampus.yanabada.common.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /* ControllerAdvice와 같은 ExHandler 역할 수행 */
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            response.setStatus(401);
            response.setContentType(APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
//            objectMapper.writeValue(response.getWriter(), StatusResponseDto.addStatus(401));
            //todo: response body 채울 예정
        }
    }
}
