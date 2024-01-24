package kr.co.fastcampus.yanabada.common.config;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

import java.util.List;
import kr.co.fastcampus.yanabada.common.jwt.filter.JwtAuthFilter;
import kr.co.fastcampus.yanabada.common.jwt.filter.JwtAuthenticationEntryPoint;
import kr.co.fastcampus.yanabada.common.jwt.filter.JwtExceptionFilter;
import kr.co.fastcampus.yanabada.common.security.oauth.Oauth2LoginFailureHandler;
import kr.co.fastcampus.yanabada.common.security.oauth.Oauth2LoginSuccessHandler;
import kr.co.fastcampus.yanabada.common.security.oauth.Oauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final JwtExceptionFilter jwtExceptionFilter;
    private final Oauth2UserService oauth2UserService;
    private final Oauth2LoginSuccessHandler oauth2LoginSuccessHandler;
    private final Oauth2LoginFailureHandler oauth2LoginFailureHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;


    private static final String[] PERMIT_PATHS = {
        "/auth", "/auth/**", "/login/**",
        "/oauth2/**", "/signin/**", "/error/**"
    };

    private static final String[] PERMIT_PATHS_POST_METHOD = {
        "/accommodations/**", "/orders"
    };

    private static final String[] PERMIT_PATHS_GET_METHOD = {
        "/products", "/products/**"
    };

    private static final String[] ALLOW_ORIGINS = {
        "http://localhost:8080",
        "http://localhost:5173",
        "https://yanabada-fe-1r96.vercel.app"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.httpBasic(AbstractHttpConfigurer::disable);
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        );

        http.authorizeHttpRequests(authorize -> authorize
            .requestMatchers(PERMIT_PATHS).permitAll()
            .requestMatchers(POST, PERMIT_PATHS_POST_METHOD).permitAll()
            .requestMatchers(GET, PERMIT_PATHS_GET_METHOD).permitAll()
            .requestMatchers("/products/own").denyAll()
            .anyRequest().authenticated()
        );

        http.exceptionHandling(exceptionHandling -> {
            exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint);
        });

        http.oauth2Login(oauth2 -> oauth2
            .userInfoEndpoint(
                userInfoEndpoint -> userInfoEndpoint.userService(oauth2UserService))
            .successHandler(oauth2LoginSuccessHandler)
            .failureHandler(oauth2LoginFailureHandler)
        );

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtExceptionFilter, JwtAuthFilter.class);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(ALLOW_ORIGINS)); // TODO: 5173 open
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.addExposedHeader("Authorization");
        configuration.setAllowCredentials(true);    //쿠키를 포함한 크로스 도메인 요청을 허용
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    @ConditionalOnProperty(name = "spring.h2.console.enabled", havingValue = "true")
    public WebSecurityCustomizer configureH2ConsoleEnable() {
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toH2Console());
    }

}
