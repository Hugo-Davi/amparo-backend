package com.tatu.mulher.Posts.services;

import com.tatu.mulher.models.Comment;
import com.tatu.mulher.models.Post;
import com.tatu.mulher.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public List<Post> getAll(){return repository.findAll();}
    public void delete(String id) {
        repository.deleteById(id);
    }
    public Post update(Post post) {
        return repository.save(post);
    }
    public boolean existById(String id) { return repository.existsById(id); }
    public void postComment(String id, Comment comment) {
        Post post = this.get(id);
        // define como data de agora
        comment.setCommentDate();
        List<Comment> newComments = post.getComments();
        // Se não existe uma sessão de comentários, cria uma vazia
        if (newComments == null) {
            newComments = new ArrayList<>();
        }
        newComments.add(comment);
        post.setComments(newComments);

        // atualiza todo o documento de post, porém com um novo comentário
        this.update(post);
    }
}
