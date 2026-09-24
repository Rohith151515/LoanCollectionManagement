package com.loan.collection.management.LoanCollectionManagement.Config;

import com.loan.collection.management.LoanCollectionManagement.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey secretKey;
    private final long expiration;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expiration
    ) {

        if (secret == null || secret.length() < 48) {
            throw new IllegalArgumentException(
                    "jwt.secret must contain at least 48 characters for HS384"
            );
        }

        this.secretKey = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );

        this.expiration = expiration;
    }

    /**
     * Generate JWT token
     */
    public String generateToken(User user) {

        return Jwts.builder()
                .subject(user.getId().toString())

                .claim("mobile", user.getMobile())

                .claim("role", user.getRole().name())

                .issuedAt(new Date())

                .expiration(
                        new Date(
                                System.currentTimeMillis() + expiration
                        )
                )

                // Explicitly use HS384
                .signWith(
                        secretKey,
                        Jwts.SIG.HS384
                )

                .compact();
    }

    /**
     * Extract all claims from JWT
     */
    public Claims extractClaims(String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Extract user ID
     */
    public String extractUserId(String token) {

        return extractClaims(token)
                .getSubject();
    }

    /**
     * Extract role
     */
    public String extractRole(String token) {

        return extractClaims(token)
                .get("role", String.class);
    }

    /**
     * Validate JWT
     */
    public boolean isTokenValid(String token) {

        try {

            Claims claims = extractClaims(token);

            Date expirationDate = claims.getExpiration();

            if (expirationDate == null) {
                System.out.println("JWT has no expiration");
                return false;
            }

            if (expirationDate.before(new Date())) {
                System.out.println("JWT TOKEN EXPIRED");
                return false;
            }

            System.out.println("========== JWT VALID ==========");
            System.out.println("User ID : " + claims.getSubject());
            System.out.println("Mobile  : " + claims.get("mobile"));
            System.out.println("Role    : " + claims.get("role"));
            System.out.println("Issued  : " + claims.getIssuedAt());
            System.out.println("Expires : " + claims.getExpiration());
            System.out.println("===============================");

            return true;

        } catch (Exception e) {

            System.out.println("========== JWT INVALID ==========");
            System.out.println(
                    "Exception : " +
                            e.getClass().getName()
            );
            System.out.println(
                    "Message   : " +
                            e.getMessage()
            );
            System.out.println("=================================");

            return false;
        }
    }
}