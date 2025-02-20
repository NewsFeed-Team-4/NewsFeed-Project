package com.example.newsfeedproject.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtProvider {
    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.expiration}")
    private String expiration;
    private final SecretKey secretKey;

    public JwtProvider(@Value("${jwt.secretKey}") String secretKey) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Payload payload) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("id", payload.getId());
        claims.put("email", payload.getEmail());
        claims.put("username", payload.getUsername());

        return Jwts.builder()
                .issuer(issuer)
                .claims(claims)
                .issuedAt(payload.getIssuedAt())
                .expiration(new Date(payload.getIssuedAt().getTime() + Long.parseLong(expiration)))
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    public Payload parseToken(String token) {
        Jws<Claims> claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token);

        return new Payload(claims.getPayload());
    }
}
