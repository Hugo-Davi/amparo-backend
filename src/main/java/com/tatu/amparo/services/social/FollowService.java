package com.tatu.amparo.services.social;

import com.tatu.amparo.models.collections.Follow;
import com.tatu.amparo.models.collections.User;
import com.tatu.amparo.repositories.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowService {
    @Autowired
    private FollowRepository repository;

    public void follow(String followerId, String followedId){
        Follow newFollow = new Follow();
        newFollow.setFollower(followerId);
        newFollow.setFollowed(followedId);
        repository.save(newFollow);
    }
    public void unfollow(String followerId, String followedId) {
        repository.deleteFollow(followerId, followedId);
    }
}
