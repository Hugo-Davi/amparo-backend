package com.tatu.amparo.controllers;

import com.google.gson.Gson;
import com.tatu.amparo.models.Comment;
import com.tatu.amparo.models.Post;
import com.tatu.amparo.services.PostService;
import com.tatu.amparo.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/post")
public class PostController {

    @Autowired
    private PostService service;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<Post>> getAll (){
        return ResponseEntity.ok(this.service.getAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Post> getById(@PathVariable("id") String id) {
        return ResponseEntity.ok(this.service.get(id));
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Post> create (@RequestBody Post post){
        return ResponseEntity.ok(this.service.save(post));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Post> update (@PathVariable("id") String id, @RequestBody Post post){

        if (!this.service.existById(id)){
            return ResponseEntity.notFound().build();
        }
        post.setId(id);
        this.service.save(post);

        Response response = new Response("Usuário atualizado");
        return ResponseEntity
                .ok(post);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Post> delete(@PathVariable String id) {
        Post post = this.service.get(id);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        this.service.delete(id);
        return ResponseEntity
                .ok(post);
    }

    @RequestMapping(value = "/{id}/comment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Comment> createComment (@PathVariable String id, @RequestBody Comment comment){
        this.service.postComment(id, comment);
        return ResponseEntity.ok(comment);
    }
}
