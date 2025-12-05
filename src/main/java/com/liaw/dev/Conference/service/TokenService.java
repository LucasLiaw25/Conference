package com.liaw.dev.Conference.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.liaw.dev.Conference.dto.JWTDetails;
import com.liaw.dev.Conference.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {

    public String generateToken(User user){
        Algorithm algorithm = Algorithm.HMAC256("heheboy");

        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim("user_id", user.getId())
                .withClaim("user_name", user.getName())
                .withClaim("user_role", String.valueOf(user.getRole()))
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusSeconds(86400))
                .sign(algorithm);
    }

    public Optional<JWTDetails> verifyToken(String token){
        Algorithm algorithm = Algorithm.HMAC256("heheboy");
        DecodedJWT jwt = JWT.require(algorithm).build().verify(token);
        return Optional.of(
                JWTDetails.builder()
                        .email(jwt.getSubject())
                        .id(jwt.getClaim("user_id").asLong())
                        .name(jwt.getClaim("user_name").asString())
                        .role(jwt.getClaim("user_role").asString())
                        .build()
        );
    }

}
