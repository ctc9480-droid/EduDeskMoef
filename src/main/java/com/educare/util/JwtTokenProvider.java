package com.educare.util;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtTokenProvider {

    // 안전한 랜덤 키 (서버에 고정 저장)
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // 만료 시간 5분
    private static final long EXPIRATION_TIME = 5 * 60 * 1000;

    public static String generateResetToken(String userId) {

        Date now = new Date();
        Date expiry = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(userId)  // 토큰에 userId 저장
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(SECRET_KEY)
                .compact();
    }
    
    public static String getUserIdFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getSubject(); // userId

        } catch (ExpiredJwtException e) {
            throw new RuntimeException("토큰이 만료되었습니다.");
        } catch (JwtException e) {
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }
    }



}
