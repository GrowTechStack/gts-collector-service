package com.jun0x2dev.devfeed.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 기본 보안 설정
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 비활성화 (API 서버)
                .csrf(AbstractHttpConfigurer::disable)
                
                // H2 Console 접속을 위한 프레임 옵션 비활성화
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                
                // 요청 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/admin", "/contents/**", "/error").permitAll() // UI 및 에러 페이지 허용
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/favicon.ico", "/favicon.svg").permitAll() // 정적 리소스 허용
                        .requestMatchers("/h2-console/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/api/**").permitAll() // MVP 단계에서는 API 전체 허용
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
