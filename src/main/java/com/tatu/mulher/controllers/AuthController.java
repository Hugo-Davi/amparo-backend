package com.tatu.mulher.controllers;

import com.tatu.mulher.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String authenticateAndGetToken(@RequestBody String username){
        return jwtService.generateToken(username);
    }
}
