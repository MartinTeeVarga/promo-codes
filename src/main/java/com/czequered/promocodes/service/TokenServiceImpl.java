package com.czequered.promocodes.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static io.jsonwebtoken.Claims.EXPIRATION;
import static io.jsonwebtoken.Claims.SUBJECT;

/**
 * @author Martin Varga
 */
@Service
public class TokenServiceImpl implements TokenService {

    private String secret;
    private ClockService clockService;
    private Long expiration;

    @Autowired TokenServiceImpl(@Value("${jepice.jwt.expiry}") Long expiration,
                                @Value("${jepice.jwt.secret}") String secret,
                                ClockService clockService) {
        this.expiration = expiration;
        this.secret = secret;
        this.clockService = clockService;
    }

    @Override public String getUserIdFromToken(String token) throws InvalidTokenException {
        try {
            final Claims claims = getClaimsFromToken(token);
            String subject = claims.getSubject();
            if (StringUtils.isEmpty(subject)) {
                throw new IllegalArgumentException("UserId must be present.");
            }
            return subject;
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid userId.", e);
        }
    }

    @Override public String generateToken(String userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(SUBJECT, userId);
        claims.put(EXPIRATION, generateExpirationDate());
        return this.generateToken(claims);
    }

    @Override public void validateToken(String token) throws InvalidTokenException {
        if (StringUtils.isEmpty(token)) {
            throw new InvalidTokenException("Empty token.");
        }
        try {
            getClaimsFromToken(token);
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid token.", e);
        }
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setClock(() -> new Date(clockService.getClock().millis()))
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    private Date generateExpirationDate() {
        return new Date(clockService.getClock().millis() + expiration);
    }

    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}
