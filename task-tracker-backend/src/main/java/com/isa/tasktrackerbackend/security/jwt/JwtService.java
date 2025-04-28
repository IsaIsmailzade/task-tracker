package com.isa.tasktrackerbackend.security.jwt;

import com.isa.tasktrackerbackend.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class JwtService {

    private final JwtProperties jwtProperties;

    public String generateAccessToken(User user) {
        Date currentTime = new Date();
        Date expirationTime = new Date(currentTime.getTime() + jwtProperties.getAccessTokenExpiration().toMillis());

        JwtBuilder jwtBuilder = Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(currentTime)
                .expiration(expirationTime)
                .signWith(getSigningKey(), Jwts.SIG.HS256);

        return jwtBuilder.compact();
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getUsernameFromToken(String token) {
        return getAllClaims(token).getSubject();
    }

    public Date getExpirationDateFromToken(String token) {
        return getAllClaims(token).getExpiration();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return !isAccessTokenExpired(token) && username.equals(userDetails.getUsername());
    }

    private boolean isAccessTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
