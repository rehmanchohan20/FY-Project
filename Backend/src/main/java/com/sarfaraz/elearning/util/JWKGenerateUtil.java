package com.sarfaraz.elearning.util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import com.sarfaraz.elearning.model.User;

public class JWKGenerateUtil {
    /**
     *
     * @param authentication
     * @return token
     */
    public static String generateToken(Authentication authentication, JwtEncoder jwtEncoder) {
        Instant timestamp = Instant.now();
        String scope = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        User user = (User) authentication.getPrincipal();
        JwtClaimsSet claims = JwtClaimsSet.builder().issuer("self").id(user.getId().toString()).issuedAt(timestamp)
                .expiresAt(timestamp.plus(10, ChronoUnit.HOURS)).subject(user.getUsername()).claim("scope", scope)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}