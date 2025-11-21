package com.example.hrm.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


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


    //Giải mã token lấy Claims





}
