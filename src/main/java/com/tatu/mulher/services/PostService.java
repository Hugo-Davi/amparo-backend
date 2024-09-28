package com.tatu.mulher.services;

import com.tatu.mulher.models.Post;
import com.tatu.mulher.models.User;
import com.tatu.mulher.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository repository;

    public Post save(Post post) {
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
}
