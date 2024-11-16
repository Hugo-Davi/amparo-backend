package com.tatu.amparo.controllers;

import com.tatu.amparo.models.User;
import com.tatu.amparo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService service;

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAll(){
        return ResponseEntity.ok(this.service.getAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getById(@PathVariable("id") String id) {
        return ResponseEntity.ok(this.service.get(id));
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> create (@RequestBody User user){
        return ResponseEntity.ok(this.service.save(user));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> update (@PathVariable("id") String id, @RequestBody User user){

        if (!this.service.existById(id)){
            return ResponseEntity.notFound().build();
        }
        user.setId(id);
        this.service.save(user);

        return ResponseEntity
                .ok(user);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> delete(@PathVariable String id) {
        User user = this.service.get(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        this.service.delete(id);

        return ResponseEntity
                .ok(user);
    }

}