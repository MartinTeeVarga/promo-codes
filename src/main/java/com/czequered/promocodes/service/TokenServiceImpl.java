package com.czequered.promocodes.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Clock;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static io.jsonwebtoken.Claims.EXPIRATION;
import static io.jsonwebtoken.Claims.SUBJECT;

/**
 * @author Martin Varga
 */
@Component
public class TokenServiceImpl implements TokenService {

    private String secret;
    private Long expiration;
    private Clock clock;

    @Autowired TokenServiceImpl(@Value("${jepice.jwt.expiry}") Long expiration,
                                @Value("${jepice.jwt.secret}") String secret,
                                Clock clock) {
        this.expiration = expiration;
        this.secret = secret;
        this.clock = clock;
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
        try {
            getClaimsFromToken(token);
        } catch (ExpiredJwtException e) {
            throw new InvalidTokenException("Expired token.", e);
        }
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setClock(() -> new Date(clock.millis()))
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    private Date generateExpirationDate() {
        return new Date(clock.millis() + expiration);
    }

    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}
