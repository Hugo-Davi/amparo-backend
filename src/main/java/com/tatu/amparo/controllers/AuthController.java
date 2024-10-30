package com.tatu.amparo.controllers;

import com.tatu.amparo.controllers.dto.LoginRequest;
import com.tatu.amparo.controllers.dto.LoginResponse;
import com.tatu.amparo.models.User;
import com.tatu.amparo.services.UserService;
import org.apache.catalina.Role;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.StreamSupport.stream;


@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){

        List<User> user = userService.findByUsername(loginRequest.username()); // Espera uma Lista de 1 ocorrência

        if (user.isEmpty()){
            throw new BadCredentialsException("user or password is invalid");
        }
        if (!user.get(0).isLoginCorrect(loginRequest, passwordEncoder)) {
            throw new BadCredentialsException("user or password is invalid");
        }
        Instant now = Instant.now();
        long expiresIn = 300L; // TEMPO DE EXPIRAÇÃO

        String scopes = user.get(0).getRoles().stream()
                        .map(String::valueOf) // Lambda function para transformar cada "ROLE" em String
                        .collect(Collectors.joining(", "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("amparo-backend")
                .subject(user.get(0).getId())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scopes)
                .build();

        String jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn));
    }

    @Transactional
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> register(@RequestBody User user){
        // CHECA SE JÁ EXISTE USUÁRIO
        if (!userService.findByUsername(user.getName()).isEmpty()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        userService.save(user);
        return ResponseEntity.ok().build();
    }

}
