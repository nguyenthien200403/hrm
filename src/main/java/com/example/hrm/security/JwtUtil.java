package com.example.hrm.security;

import com.example.hrm.model.InviteEmployee;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {


    @Value("${jwt.expiration}")
    private long expirationMillis;


    @Value("${jwt.secret}")
    private String secret;

    private Key key;

    @PostConstruct
    public void init() {
        if (secret == null) {
            throw new IllegalStateException("JWT Secret is null! Check application.properties.");
        }
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    // Tạo JWT token
    public String generateToken(InviteEmployee inviteEmployee) {
        return Jwts.builder()
                .setSubject(inviteEmployee.getEmail())
                .claim("name", inviteEmployee.getName())
                .claim("status", inviteEmployee.getStatus())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    //Giải mã token lấy Claims
    public Claims extractClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }




}
