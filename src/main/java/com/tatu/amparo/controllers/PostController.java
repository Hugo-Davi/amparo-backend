package com.tatu.amparo.controllers;

import com.tatu.amparo.dto.post.CommentAddRequest;
import com.tatu.amparo.models.collections.Post;
import com.tatu.amparo.models.collections.User;
import com.tatu.amparo.services.social.CommentService;
import com.tatu.amparo.services.social.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/post")
public class PostController {

    @Autowired
    private PostService service;

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public ResponseEntity<List<Post>> getMy (JwtAuthenticationToken token){
        if(token.getName() == null){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(this.service.getPostByCreator(token.getName()));
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<Post>> getAll (){
        return ResponseEntity.ok(this.service.getAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Post> getById(@PathVariable("id") String id) {
        return ResponseEntity.ok(this.service.get(id));
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Post> create (@RequestBody Post post, JwtAuthenticationToken token){

        if(token.getName() == null){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        User user = new User(token.getName());
        post.setCreator(user);

        return ResponseEntity.ok(this.service.createPost(post));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Post> update (@PathVariable("id") String id, @RequestBody Post post){

        if (!this.service.existById(id)){
            return ResponseEntity.notFound().build();
        }
        post.setId(id);
        this.service.save(post);

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
    public ResponseEntity<Void> postComment (@PathVariable String id, @RequestBody CommentAddRequest commentAddRequest, JwtAuthenticationToken token){
        this.commentService.postComment(id, commentAddRequest.text(), token.getName());
        return ResponseEntity.ok().build();
    }
}
