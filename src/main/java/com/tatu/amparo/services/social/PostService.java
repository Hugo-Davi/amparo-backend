package com.tatu.amparo.services.social;

import com.tatu.amparo.dto.follow.FollowGetByFollower;
import com.tatu.amparo.models.collections.Post;
import com.tatu.amparo.repositories.FollowRepository;
import com.tatu.amparo.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private FollowRepository followRepository;

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

    public Post createPost(Post post) {
        post.setCreationDate();
        post.setComments(new ArrayList<>());
        return repository.save(post);
    }

    public List<Post> getPostByFollows(String userId){
        List<FollowGetByFollower> followedUsers;
        followedUsers = followRepository.getByFollower(userId).orElse(null);
        if(followedUsers == null){
            return null;
        }
        List<String> followedIds = new ArrayList<String>();
        for(int i = 0; i < followedUsers.size(); i++){
           followedIds.add(i, followedUsers.get(i).followed());
        }
        return repository.getPostByCreators(followedIds).orElse(null);
    }
    public List<Post> getPostByCreator(String userId) {
        return repository.getPostByCreator(userId).orElse(null);
    }
}