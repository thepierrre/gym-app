package com.example.gymapp.security;

import org.springframework.beans.factory.annotation.Value;

public class SecurityConstants {

    public static final long JWT_EXPIRATION = 70000;
    public static String JWT_SECRET;

    @Value("${jwt.secret}")
    public void setJwtSecret(String jwtSecret) {
        JWT_SECRET = jwtSecret;
    }

}
