package com.tatu.amparo.controllers;

import com.tatu.amparo.controllers.dto.LoginRequest;
import com.tatu.amparo.controllers.dto.LoginResponse;
import com.tatu.amparo.controllers.dto.RegisterUserRequest;
import com.tatu.amparo.models.User;
import com.tatu.amparo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
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

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("amparo-backend")
                .subject(user.get(0).getId())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .build();

        String jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> register(@RequestBody RegisterUserRequest userRequest){
        System.out.println(userRequest.password() + " " + userRequest.username());
        return ResponseEntity.ok().build();
    }

}
