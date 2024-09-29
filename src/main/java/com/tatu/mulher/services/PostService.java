package com.tatu.mulher.services;

import com.mongodb.client.MongoCollection;
import com.tatu.mulher.models.Comment;
import com.tatu.mulher.models.Post;
import com.mongodb.client.model.Updates;
import com.tatu.mulher.models.User;
import com.tatu.mulher.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository repository;

    public Post save(Post post) {
        post.setCreationDate();
        post.setComments(new ArrayList<>());
        return repository.save(post);
    }
    public Post get(String id) {
        return repository.findById(id).orElse(null);
    }
    public List<Post> getAll(){
        return repository.findAll();
    }
    public void delete(String id) {
        repository.deleteById(id);
    }
    public Post update(Post post) {
        return repository.save(post);
    }

    public void postComment(String id, Comment comment) {
        Post post = this.get(id);
        // define como data de agora
        comment.setCommentDate();
        List<Comment> newComments = post.getComments();
        if (newComments == null) {
            newComments = new ArrayList<>();
        }
        // adicionar novo comentário
        newComments.add(comment);
        //newComments.add(comment);
        post.setComments(newComments);
        // atualiza todo o documento de post com novo comentário
        this.update(post);
    }
}
