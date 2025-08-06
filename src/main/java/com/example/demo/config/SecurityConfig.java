package com.example.demo.config;

import com.example.demo.repository.MemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import java.util.List;

@Configuration
@EnableWebSecurity

public class SecurityConfig {

    private final MemberRepository memberRepository;

    public SecurityConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
         // 1) CORS 설정 (람다 DSL)
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        // 2) CSRF 비활성화 (람다 DSL)
        .csrf(AbstractHttpConfigurer::disable)
        // 3) URL 권한 설정
        .authorizeHttpRequests(auth -> auth
            // 1) static 리소스
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
            // 2) SPA 최상위 리소스
            .requestMatchers("/", "/index.html", "/favicon.ico", "/manifest.json").permitAll()
            // 3) OAuth2 진입점
            .requestMatchers("/oauth2/**", "/login/oauth2/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/**").permitAll()
            // 4) 전체 방 조회(GET)은 모두 허용
            .requestMatchers(HttpMethod.GET, "/api/groups", "/api/groups/**").permitAll()
            // 5) 방 생성(POST /api/groups)도 허용
            .requestMatchers(HttpMethod.POST, "/api/groups").permitAll()
            // 6) 참가 요청(POST /api/groups/{id}/join)도 허용
            .requestMatchers(HttpMethod.POST, "/api/groups/*/join").permitAll()

            // 6) 나머지 API는 인증
            .requestMatchers("/api/**").authenticated()
            // 7) 그 외 모든 요청은 인증
            .anyRequest().authenticated()
        )

        .exceptionHandling(ex -> ex
            .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
        )

         // 4) OAuth2 로그인 설정
        .oauth2Login(oauth2 -> oauth2
            // 카카오 로그인 시작 URL
            .loginPage("/oauth2/authorization/kakao")
            // CustomOAuth2UserService를 등록
            .userInfoEndpoint(userInfo -> userInfo.userService(new CustomOAuth2UserService(memberRepository))
            )
            // 로그인 성공 시 리다이렉트 URL
            .defaultSuccessUrl("https://localhost:3000", true)
        )
        // 5) 로그아웃 설정
        .logout(logout -> logout .logoutSuccessUrl("/"));


    return http.build();
}

/**
     * CORS 설정 빈을 등록. 프론트(3000번 포트)에서 오는 요청을 /api/** 경로로 허용한다.
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        // React 개발 서버 주소
        // (1) 허용하려는 출처(Origin) 목록 
        config.setAllowedOrigins(List.of("http://localhost:3000","https://localhost:3000"));
        // (2) 허용할 HTTP 메서드
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // (3) 허용할 헤더 (예: 인증 헤더를 사용한다면 "Authorization" 등 추가)
        config.setAllowedHeaders(List.of("*"));
        // (4) 자격 증명 허용 여부 (쿠키, 인증 헤더 사용 시 true)
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // /api/**로 들어오는 모든 요청에 위 CORS 설정을 적용
        source.registerCorsConfiguration("/api/**", config);
        return source;
    }
}
