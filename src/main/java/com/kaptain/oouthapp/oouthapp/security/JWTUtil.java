package com.kaptain.oouthapp.oouthapp.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Service;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Date;

@Service
public class JWTUtil {

    KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);

    @Value("${jwt.expiration.time}")
    private Long jwtExpirationMills;

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey((RSAPublicKey) keyPair.getPublic()).build();
    }

    public String generateTokenForPatient(String patientId) {
        return Jwts.builder()
                .setIssuer("oouthapp")
                .setSubject(patientId)
                .setAudience(patientId)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusMillis(getJwtExpirationMills())))
                .claim("scope", "PATIENT")
                .signWith(keyPair.getPrivate())
                .compact();
    }

    public String generateTokenForDoctors(String email) {
        return Jwts.builder()
                .setIssuer("oouthapp")
                .setSubject(email)
                .setAudience(email)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusMillis(getJwtExpirationMills())))
                .claim("scope", "DOCTOR")
                .signWith(keyPair.getPrivate())
                .compact();
    }

    public String generateTokenForAdmin(String email) {
        return Jwts.builder()
                .setIssuer("oouthapp")
                .setSubject(email)
                .setAudience(email)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusMillis(getJwtExpirationMills())))
                .claim("scope", "ADMIN")
                .signWith(keyPair.getPrivate())
                .compact();
    }

    public Long getJwtExpirationMills() { return this.jwtExpirationMills; }

}
