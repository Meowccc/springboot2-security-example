package com.example.demo.security;

import com.example.demo.property.TokenProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

/**
 * @author meow
 */
@Component
@RequiredArgsConstructor
public class TokenUtil {

    private final TokenProperties tokenProperties;

    @Value("")

    private Key getSigningKey() {
        byte[] keyBytes = tokenProperties.getSecret().getBytes(StandardCharsets.UTF_8);
        final String serviceCode = tokenProperties.getServiceCode();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateJwtToken(final String subject) {
        return generateToken(subject, tokenProperties.getJwtExpirationMs());
    }

    public String generateJwtRefreshToken(final String subject) {
        return generateToken(subject, tokenProperties.getJwtRefreshExpirationMs());
    }

    private String generateToken(final String subject, final long expirationMs) {
        final Date now = new Date();
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expirationMs))
                .compact();
    }

    public Claims getClaims(final String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build().parseClaimsJws(token).getBody();
    }

    public String getSubject(final String token) {
        return getClaims(token)
                .getSubject();
    }

    public void verifyToken(String authToken) {
        Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(authToken);
    }
}
