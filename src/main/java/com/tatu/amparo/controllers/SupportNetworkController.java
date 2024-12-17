package com.tatu.amparo.controllers;

import com.tatu.amparo.models.fields.SupportNetwork;
import com.tatu.amparo.services.support.SupportNetworkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "support network")
@RestController
@RequestMapping(value = "/sup")
public class SupportNetworkController {
    @Autowired
    private SupportNetworkService service;

    @Operation(summary = "Get the logged user Support Network")
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SupportNetwork>> getSupportNetwork (JwtAuthenticationToken token){

        System.out.println(token.getName());
        if(token.getName() == null){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.ok(this.service.getSupportNetwork(token.getName()));
    }

    @Operation(summary = "Update the Support Network")
    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SupportNetwork>> updateSupportNetwork (@RequestBody List<SupportNetwork> supportNetwork, JwtAuthenticationToken token) {
        if(token.getName() == null){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.ok(this.service.updateSupportNetwork(token.getName(), supportNetwork));
    }
}
