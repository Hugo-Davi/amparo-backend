package com.tatu.amparo.controllers;

import com.tatu.amparo.services.social.FollowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Tag(name = "follow")
@RestController
@RequestMapping(value = "/follow")
public class FollowController {

    @Autowired
    private FollowService service;

    @Operation(summary = "Follow user")
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity<Void> follow(@PathVariable("id") String followedId,
                                        JwtAuthenticationToken token){
        if (token.getName() == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        if (this.service.existFollow(token.getName(), followedId)){
            throw new ResponseStatusException(HttpStatus.FOUND);
        }
        this.service.follow(token.getName(), followedId);
        return ResponseEntity.ok().build();
    }
    @Operation(summary = "Unfollow user")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> unFollow(@PathVariable("id") String followedId,
                                         JwtAuthenticationToken token){
        if (token.getName() == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        this.service.unfollow(token.getName(), followedId);
        return ResponseEntity.ok().build();
    }
}