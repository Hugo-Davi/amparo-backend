package com.tatu.mulher.controllers;

import com.google.gson.Gson;
import com.tatu.mulher.models.User;
import com.tatu.mulher.services.UserService;
import com.tatu.mulher.utils.Response;
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
        String response = new Gson().toJson(this.userService.getAllUsers());
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getUserById(@PathVariable("id") String id) {
        String response = new Gson().toJson(this.userService.getUser(id));
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createUser (@RequestBody User user){
        this.userService.saveUser(user);
        Response response = new Response("Usuário foi salvo");
        return ResponseEntity.ok(new Gson().toJson(response));
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateUser (@RequestBody User user){
        // Checa se ID existe na estrutura passada
        if (user.getId() == null){
            Response response = new Response("ID não fornecido");
            return ResponseEntity
                    .unprocessableEntity().body(new Gson().toJson(response));
        }

        this.userService.saveUser(user);

        Response response = new Response("Usuário atualizado");
        return ResponseEntity
                .ok(new Gson().toJson(response));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> updateUser(@PathVariable String id) {
        User user = this.userService.getUser(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        this.userService.deleteUser(id);
        String response = new Gson().toJson(user);

        return ResponseEntity.ok(response);
    }

}