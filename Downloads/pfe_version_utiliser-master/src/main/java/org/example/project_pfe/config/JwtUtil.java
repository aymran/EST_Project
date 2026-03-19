package org.example.project_pfe.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey secretKey;
    private JwtParser parser;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.parser    = Jwts.parser().verifyWith(secretKey).build();
    }

    public String generateToken(String email, String role) {
        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey)
                .compact();
    }

    public String extractEmail(String token) {
        return parser
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public String extractRole(String token) {
        return (String) parser
                .parseSignedClaims(token)
                .getPayload()
                .get("role");
    }

    public boolean isTokenValid(String token) {
        try {
            parser.parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println(">>> TOKEN EXPIRED: " + e.getMessage());
        } catch (Exception e) {
            System.out.println(">>> TOKEN INVALID: " + e.getMessage());
        }
        return false;
    }
}