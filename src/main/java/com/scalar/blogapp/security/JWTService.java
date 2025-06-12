package com.scalar.blogapp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JWTService {

    private final Algorithm algorithm;
    private final JWTVerifier verifier;
    private final long jwtExpirationMinutes;

    public JWTService(
            @Value("${jwt.secret}") String jwtSecret,
            @Value("${jwt.expiration.minutes}") long jwtExpirationMinutes) {
        this.algorithm = Algorithm.HMAC256(jwtSecret);
        this.verifier = JWT.require(this.algorithm)
                .build();
        this.jwtExpirationMinutes = jwtExpirationMinutes;
    }

    public String createJwt(String username) {
        Instant now = Instant.now();
        Instant expiry = now.plus(jwtExpirationMinutes, ChronoUnit.MINUTES);

        return JWT.create()
                .withSubject(username)
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(expiry))
                .sign(algorithm);
    }

    public String retrieveUsername(String jwtString) {
        DecodedJWT decodedJWT = JWT.decode(jwtString);
        return decodedJWT.getSubject();
    }

    public boolean isValid(String jwtString) {
        try {
            verifier.verify(jwtString);
            return true;
        } catch (JWTVerificationException exception) {
            return false;
        }
    }
}