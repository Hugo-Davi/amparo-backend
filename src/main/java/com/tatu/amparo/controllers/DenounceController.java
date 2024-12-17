package com.tatu.amparo.controllers;

import com.tatu.amparo.models.collections.Denounce;
import com.tatu.amparo.models.collections.User;
import com.tatu.amparo.services.support.DenounceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "denounce")
@RestController
@RequestMapping(value = "/denounce")
public class DenounceController {
    @Autowired
    private DenounceService service;

    @Operation(summary = "Get all the user denounces")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<Denounce>> getAll(JwtAuthenticationToken token){
        return ResponseEntity.ok(this.service.getDenouncesByUser(token.getName()));
    }

//    @Operation(summary = "")
//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    public ResponseEntity<Denounce> getById(@PathVariable("id") String id) {
//        return ResponseEntity.ok(this.service.get(id));
//    }

    @Operation(summary = "Make Denounce")
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create (@RequestBody Denounce denounce, JwtAuthenticationToken token){
        denounce.setUser(new User(token.getName()));
        this.service.save(denounce);
        return ResponseEntity.ok().build();
    }

//    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Void> update (@PathVariable("id") String id, @RequestBody Denounce denounce){
//
//        if (!this.service.existById(id)){
//            return ResponseEntity.notFound().build();
//        }
//        denounce.setId(id);
//        this.service.save(denounce);
//
//        return ResponseEntity.ok().build();
//    }
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//    public ResponseEntity<Void> delete(@PathVariable String id) {
//        Denounce denounce = this.service.get(id);
//        if (denounce == null) {
//            return ResponseEntity.notFound().build();
//        }
//        this.service.delete(id);
//
//        return ResponseEntity
//                .ok().build();
//    }

}
