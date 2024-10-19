package com.tatu.mulher.controllers;

import com.tatu.mulher.controllers.dto.LoginRequest;
import com.tatu.mulher.controllers.dto.LoginResponse;
import com.tatu.mulher.models.User;
import com.tatu.mulher.services.JwtService;
import com.tatu.mulher.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;


@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){

        List<User> user = userService.findByUsername(loginRequest.username()); // Espera uma Lista de 1 ocorrência

        if (user.isEmpty() || !user.get(0).isLoginCorrect(loginRequest, passwordEncoder)){
            throw new BadCredentialsException("user or password is invalid");
        }

        Instant now = Instant.now();
        long expiresIn = 300L; // TEMPO DE EXPIRAÇÃO

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("amparo-backend")
                .subject(user.get(0).getId())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .build();

        String jwtValue = jwtService.jwtEncoder().encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn));
    }
}
