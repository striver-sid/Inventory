package com.example.demo.Components;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    private static final long ACCESS_TOKEN_EXPIRATION=1000*60*2;//2 mins
    private static final long REFRESH_TOKEN_EXPIRATION=1000*60*60*24*7;//7 days
    private static final Key SECRET_KEY=Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String generateAccessToken(Map<String,Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims)//for role
                .setSubject(subject)//for userId and email
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ACCESS_TOKEN_EXPIRATION))
                .signWith(SECRET_KEY)
                .compact();

    }

    public static String generateRefreshToken(String subject){
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+REFRESH_TOKEN_EXPIRATION))
                .signWith(SECRET_KEY)
                .compact();
    }




}
