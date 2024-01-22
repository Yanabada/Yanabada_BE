package kr.co.fastcampus.yanabada.common.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class CustomConfig extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (isInvalidPath(request.getRequestURI())) {
            // 잘못된 경로에 대한 처리
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404 상태 코드 설정
            response.getWriter().write("잘못된 경로입니다.");
            return; // 필터 체인의 나머지 부분을 실행하지 않음
        }
        filterChain.doFilter(request, response);
    }

    private boolean isInvalidPath(String path) {
        // 여기에 유효한 경로를 검사하는 로직 추가
        // 예시: "/valid-path"로 시작하는 경로만 허용
        return !path.startsWith("/auth") ||
            !path.startsWith("/member") ||
            !path.startsWith("/chats") ||
            !path.startsWith("/accommodations") ||
            !path.startsWith("/valid-path") ||
            !path.startsWith("/valid-path") ||
            !path.startsWith("/valid-path") ||
            !path.startsWith("/valid-path");
    }


}
