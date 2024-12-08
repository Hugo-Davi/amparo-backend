package com.tatu.amparo.controllers;

import com.tatu.amparo.dto.auth.*;
import com.tatu.amparo.models.collections.Authentication;
import com.tatu.amparo.models.collections.RefreshToken;
import com.tatu.amparo.services.auth.AuthService;
import com.tatu.amparo.services.auth.JwtService;
import com.tatu.amparo.services.auth.RefreshTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/auth")
@Tag(name = "amparo")
public class AuthController {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Operation(summary = "Login", method = "POST")
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){

        Authentication auth = authService.findByCredential(loginRequest.credential());

        if (auth == null){
            throw new BadCredentialsException("user or password is invalid");
        }
        if (!auth.isLoginCorrect(loginRequest, passwordEncoder)) {
            throw new BadCredentialsException("user or password is invalid");
        }

        return ResponseEntity.ok(
                new LoginResponse(
                        jwtService.generateJwtToken(auth),
                        refreshTokenService.generateRefreshToken(auth.getUser().getId())
                    )
                );
    }

    @Transactional
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest){
        if (registerRequest.credential().length() == 0) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        // CHECA SE AS CREDENCIAL JÁ EXISTE
        if (authService.existCredential(registerRequest.credential())){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Authentication auth = authService.registerUser(registerRequest);
        return ResponseEntity.ok(
                new RegisterResponse(
                        jwtService.generateJwtToken(auth),
                        refreshTokenService.generateRefreshToken(auth.getUser().getId())
                )
        );
    }

    @Transactional
    @RequestMapping(value = "/refresh", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RefreshResponse> refresh(@RequestBody RefreshRequest refreshRequest){
        if (refreshRequest.credential().length() == 0) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        // CHECA SE CREDENTIAL EXISTE
        if (!authService.existCredential(refreshRequest.credential())){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        Authentication auth = authService.findByCredential(refreshRequest.credential());
        RefreshToken refreshToken = refreshTokenService.getRefreshToken(refreshRequest.refreshToken(), auth.getUser().getId());
        if (refreshToken.getToken().length() == 0) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return ResponseEntity.ok(
                new RefreshResponse(
                        jwtService.generateJwtToken(auth),
                        refreshTokenService.generateRefreshToken(refreshToken.getUser().getId())
                )
        );
    }

}
