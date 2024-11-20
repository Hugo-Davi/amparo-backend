package com.tatu.amparo.controllers;

import com.tatu.amparo.models.SupportNetwork;
import com.tatu.amparo.models.User;
import com.tatu.amparo.services.SupportNetworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/sup")
public class SupportNetworkController {
    @Autowired
    private SupportNetworkService service;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SupportNetwork>> getSupportNetwork (JwtAuthenticationToken token){

        System.out.println(token.getName());
        if(token.getName() == null){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.ok(this.service.getSupportNetwork(token.getName()));
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SupportNetwork>> removeSupportNetwork (@RequestBody List<SupportNetwork> supportNetwork, JwtAuthenticationToken token) {
        if(token.getName() == null){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.ok(this.service.addSupportNetwork(token.getName(), supportNetwork));
    }
}
