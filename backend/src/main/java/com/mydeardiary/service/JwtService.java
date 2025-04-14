package com.mydeardiary.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.mydeardiary.model.user.User;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
public class JwtService {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.gmt.offset}")
    private String gmtOffset;

    public String generateToken(User user) {
        try {
            if (jwtSecret == null || jwtSecret.isBlank()) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "JWT secret está inválido (null ou vazio)");
            }
            var algorithm = Algorithm.HMAC256(jwtSecret);
            return JWT.create()
                    .withIssuer("mydeardiary")
                    .withSubject(user.getEmail())
                    .withClaim("role", user.getRole().toString())
                    .withExpiresAt(getExpirationTime())
                    .sign(algorithm);
        } catch (IllegalArgumentException | JWTCreationException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao gerar token: " + e.getMessage());
        }
    }

    public String validateToken(String token) {
        try {
            var algorithm = Algorithm.HMAC256(jwtSecret);
            return JWT.require(algorithm)
                    .withIssuer("mydeardiary")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    private Instant getExpirationTime() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of(gmtOffset));
    }
}