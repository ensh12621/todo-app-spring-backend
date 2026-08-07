package com.kkh.todoapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        
        http
            .csrf(csrf -> csrf.disable()) // REST API 개발 시 보통 비활성화
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // 모든 요청에 대해 인증 없이 접근 허용
            );
        
        return http.build();
    }
}
