package com.tatu.amparo.controllers;

import com.tatu.amparo.dto.LoginRequest;
import com.tatu.amparo.dto.LoginResponse;
import com.tatu.amparo.dto.RegisterRequest;
import com.tatu.amparo.models.Authentication;
import com.tatu.amparo.models.User;
import com.tatu.amparo.services.AuthService;
import com.tatu.amparo.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.StreamSupport.stream;


@RestController
@RequestMapping(value = "/auth")
@Tag(name = "amparo")
public class AuthController {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private AuthService authService;

    @Operation(summary = "Login", method = "POST")
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){

        Authentication auth = authService.findByCredential(loginRequest.credential()); // Espera uma Lista de 1 ocorrência

        if (auth == null){
            throw new BadCredentialsException("user or password is invalid");
        }
        if (!auth.isLoginCorrect(loginRequest, passwordEncoder)) {
            throw new BadCredentialsException("user or password is invalid");
        }
        Instant now = Instant.now();
        long expiresIn = 3000L; // TEMPO DE EXPIRAÇÃO

        String scopes = auth.getRoles().stream()
                        .map(String::valueOf) // Lambda function para transformar cada "ROLE" em String
                        .collect(Collectors.joining(", "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("amparo-backend")
                .subject(auth.getUser().getId())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scopes)
                .build();

        String jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn));
    }

    @Transactional
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> register(@RequestBody RegisterRequest registerRequest){
        if (registerRequest.credential().length() == 0) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        // CHECA SE AS CREDENCIAL JÁ EXISTE
        if (authService.existCredential(registerRequest.credential())){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        authService.registerUser(registerRequest);
        return ResponseEntity.ok().build();
    }

}
