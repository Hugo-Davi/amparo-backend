package com.tatu.mulher.controllers;

import com.google.gson.Gson;
import com.tatu.mulher.models.User;
import com.tatu.mulher.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<String> getUsers(){
        String json;
        json = new Gson().toJson(this.userService.getAllUsers());
        return ResponseEntity.ok(json);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getUserById(@PathVariable("id") String id) {
        String json;

        json = new Gson().toJson(this.userService.getUser(id));

        return ResponseEntity.ok(json);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createUser (@RequestBody User user){
        this.userService.createUser(user);
        return ResponseEntity.ok("{status:funcionou}");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> updateUser(@PathVariable String id) {
        String json;
        User user = this.userService.getUser(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        this.userService.deleteUser(id);
        json = new Gson().toJson(user);
        return ResponseEntity.ok(json);
    }

}