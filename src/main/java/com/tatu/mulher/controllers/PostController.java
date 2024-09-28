package com.tatu.mulher.controllers;

import com.google.gson.Gson;
import com.tatu.mulher.models.Post;
import com.tatu.mulher.services.PostService;
import com.tatu.mulher.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/post")
public class PostController {

    @Autowired
    private PostService service;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<String> getAll (){
        String response = new Gson().toJson(this.service.getAll());
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create (@RequestBody Post post){
        this.service.save(post);
        Response response = new Response("Post foi salvo");
        return ResponseEntity.ok(new Gson().toJson(response));
    }
}
