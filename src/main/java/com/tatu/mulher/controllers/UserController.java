package com.tatu.mulher.controllers;

import com.google.gson.Gson;
import com.tatu.mulher.models.User;
import com.tatu.mulher.services.UserService;
import com.tatu.mulher.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService service;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<String> getAll(){
        String response = new Gson().toJson(this.service.getAll());
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getById(@PathVariable("id") String id) {
        String response = new Gson().toJson(this.service.get(id));
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create (@RequestBody User user){
        this.service.save(user);
        Response response = new Response("Usuário foi salvo");
        return ResponseEntity.ok(new Gson().toJson(response));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update (@PathVariable("id") String id, @RequestBody User user){

        if (!this.service.existById(id)){
            return ResponseEntity.notFound().build();
        }
        user.setId(id);
        this.service.save(user);

        Response response = new Response("Usuário atualizado");
        return ResponseEntity
                .ok(new Gson().toJson(response));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable String id) {
        User user = this.service.get(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        this.service.delete(id);
        String response = new Gson().toJson(user);

        return ResponseEntity
                .ok(response);
    }

}