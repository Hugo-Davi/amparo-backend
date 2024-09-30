package com.tatu.mulher.controllers;

import com.google.gson.Gson;
import com.tatu.mulher.models.Comment;
import com.tatu.mulher.models.Post;
import com.tatu.mulher.models.User;
import com.tatu.mulher.services.PostService;
import com.tatu.mulher.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getById(@PathVariable("id") String id) {
        String response = new Gson().toJson(this.service.get(id));
        return ResponseEntity.ok(response);
    }
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create (@RequestBody Post post){
        this.service.save(post);
        Response response = new Response("Post foi salvo");
        return ResponseEntity.ok(new Gson().toJson(response));
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update (@PathVariable("id") String id, @RequestBody Post post){

        if (!this.service.existById(id)){
            return ResponseEntity.notFound().build();
        }
        post.setId(id);
        this.service.save(post);

        Response response = new Response("Usuário atualizado");
        return ResponseEntity
                .ok(new Gson().toJson(response));
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable String id) {
        Post post = this.service.get(id);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        this.service.delete(id);
        String response = new Gson().toJson(post);

        return ResponseEntity
                .ok(response);
    }

    @RequestMapping(value = "/{id}/comment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create (@PathVariable String id, @RequestBody Comment comment){
        this.service.postComment(id, comment);
        Response response = new Response("Comentário adicionado");
        return ResponseEntity.ok(new Gson().toJson(response));
    }
}
