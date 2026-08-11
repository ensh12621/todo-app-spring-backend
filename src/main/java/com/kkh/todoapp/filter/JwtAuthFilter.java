package com.kkh.todoapp.filter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kkh.todoapp.service.JwtService;
import com.kkh.todoapp.service.MemberExceptionHandler;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    private UserDetailsService userDetailsService;
    private MemberExceptionHandler memberExceptionHandler;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

    public JwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService,
            MemberExceptionHandler memberExceptionHandler) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.memberExceptionHandler = memberExceptionHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // logger.info("필터 진입 중..");

        /*
         * 
         * 1. 토큰을 가져온다.
         * 
         * 2. 토큰으로부터 subject를 가져온다.
         * 
         * 3. subject가 db에 있는지 조회한다.
         * 
         * 4. 있다면 token이 expiration date보다 지났는지 검사한다.
         * 
         * 5. 없다면 throw forbidden exception
         * 
         */
        logger.info("상황 1");

        String authHeader = request.getHeader("Authorization");
        String token = null, email = null;

        logger.info("bearer token received => {}", authHeader);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            email = jwtService.extractSubject(token);

            logger.info("extracted email from the jwt: {}", email);

            logger.info("토큰 길이: ({})", token.length());
            logger.info("상황 2");
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            logger.info("상황 3");
            if (jwtService.validateToken(token)) { // check expiration date
                logger.info("상황 4");
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null /* credentials */, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }
        }

        logger.info("상황 5");

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/member/login")
                || path.startsWith("/member/add")
                || path.startsWith("/member/refresh-JWT");

    }
}
