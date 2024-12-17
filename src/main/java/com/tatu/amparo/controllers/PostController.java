package com.tatu.amparo.controllers;

import com.tatu.amparo.dto.post.CommentAddRequest;
import com.tatu.amparo.models.collections.Post;
import com.tatu.amparo.models.collections.User;
import com.tatu.amparo.services.social.CommentService;
import com.tatu.amparo.services.social.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Tag(name = "post")
@RestController
@RequestMapping(value = "/post")
public class PostController {

    @Autowired
    private PostService service;

    @Autowired
    private CommentService commentService;

    @Operation(summary = "Get the logged user posts")
    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public ResponseEntity<List<Post>> getMy (JwtAuthenticationToken token){
        if(token.getName() == null){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(this.service.getPostByCreator(token.getName()));
    }

    @Operation(summary = "Get all posts")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<Post>> getAll (){
        return ResponseEntity.ok(this.service.getAll());
    }

    @Operation(summary = "Get post by id")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Post> getById(@PathVariable("id") String id) {
        return ResponseEntity.ok(this.service.get(id));
    }

    @Operation(summary = "Create post")
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Post> create (@RequestBody Post post, JwtAuthenticationToken token){

        if(token.getName() == null){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        User user = new User(token.getName());
        post.setCreator(user);

        return ResponseEntity.ok(this.service.createPost(post));
    }

    @Operation(summary = "Update post")
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

    @Operation(summary = "Delete post")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable String id) {
        Post post = this.service.get(id);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        this.service.delete(id);
        return ResponseEntity
                .ok().build();
    }

    @Operation(summary = "Get all the posts made by the followed users")
    @RequestMapping(value = "/follow", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Post>> getPostByFollows(JwtAuthenticationToken token){
        if (token.getName() == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        List<Post> posts = this.service.getPostByFollows(token.getName());
        if (posts == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(posts);
    }

    @Operation(summary = "Make a comment in the post")
    @RequestMapping(value = "/{id}/comment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> postComment (@PathVariable String id,
                                             @RequestBody CommentAddRequest commentAddRequest,
                                             JwtAuthenticationToken token) {
        this.commentService.postComment(id, commentAddRequest.text(), token.getName());
        return ResponseEntity.ok().build();
    }
}
