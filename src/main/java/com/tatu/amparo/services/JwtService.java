package com.tatu.amparo.services;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.tatu.amparo.dto.auth.LoginResponse;
import com.tatu.amparo.models.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.stream.Collectors;

@Component
public class JwtService {

    @Value("${jwt.public.key}")
    private RSAPublicKey publicKey;
    @Value("${jwt.private.key}")
    private RSAPrivateKey privateKey;

    @Value("${jwt.token.expiration}")
    private long expiresIn;
    @Value("${jwt.refresh-token.expiration}")
    private long expiresInRefresh;

    @Bean
    public JwtDecoder jwtDecoder() {
    return NimbusJwtDecoder.withPublicKey(publicKey).build();
}

    @Bean
    public JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(publicKey).privateKey(privateKey).build();
        var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    public LoginResponse generateJwtToken(Authentication auth) {
        Instant now = Instant.now();

        String scopes = auth.getRoles().stream()
                .map(String::valueOf) // Lambda function para transformar cada "ROLE" em String
                .collect(Collectors.joining(", "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("amparo-backend")
                .subject(auth.getUser().getId())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(this.expiresIn))
                .claim("scope", scopes)
                .build();

        String jwtValue = jwtEncoder().encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new LoginResponse(jwtValue, this.expiresIn);
    }
//    public static final String SECRET = "AbrnosrmBF9Kka5LGLs3GcHjOKqWi1g0lYlk32YEp7K5W9tZqr2x3Xlz";
//
//    public String generateToken(String userName){
//        Map<String, Object> claims = new HashMap<>();
//        return createToken(claims, userName);
//    }
//
//    private String createToken(Map<String, Object> claims, String userName) {
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(userName)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
//                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
//    }
//
//    private Key getSignKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }

}
