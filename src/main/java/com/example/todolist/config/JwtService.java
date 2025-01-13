package com.example.todolist.config;

import com.example.todolist.dtos.UserLoginDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtService {

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Claims claims = getClaims(token);
        Date expiration = claims.getExpiration();
        return expiration == null || expiration.before(new Date());
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public String generateToken(UserLoginDTO request) {
        return Jwts.builder()
                .setSubject(request.getUserName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            System.out.println("Token is invalid!" + e.getMessage());
            return null;
        }
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(JWT_SECRET.getBytes());
    }

}
