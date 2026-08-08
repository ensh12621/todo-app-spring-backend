package com.kkh.todoapp.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.kkh.todoapp.vo.LoginDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService {

    public static final String SECRET = "53675F452848284D623676397951655468576D5A71668597033732F423347437";

    @Override
    public String generateJwt(LoginDTO loginDTO) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(loginDTO.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignSaltKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignSaltKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String extractSubject(String token) {
        return extractFromJwt(token, claims -> claims.getSubject());
        // functioner example:
        // Function<String, Integer> stringLength = str -> str.length();
    }

    @Override
    public Date extractExpirationDate(String token) {
        return extractFromJwt(token, claims -> claims.getExpiration());
        // functioner example:
        // Function<String, Integer> stringLength = str -> str.length();
    }

    private <R> R extractFromJwt(String token, Function<Claims, R> functioner) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignSaltKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return functioner.apply(claims);
    }

    @Override
    public boolean validateToken(String token) {
        return extractExpirationDate(token).before(new Date());
    }

}
