package com.arvin.springbootjwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

public class JwtUtil {

    private static final String SECRET = "abcdefghijklmnopqrstuvwx1234567890";
    private static final Key key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    public static String createJWT(String subject, long expire){
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "H256")
                //payload
                .setId(UUID.randomUUID().toString())
                .setIssuer("Arvin")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ expire))
                .setSubject(subject)
                //signtrue
                .signWith(key)
                .compact();
    }

    public static Claims praseJWT(String jwtToken){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }
}
