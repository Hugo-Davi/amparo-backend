package com.tatu.amparo.services.social;

import com.tatu.amparo.repositories.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowService {
    @Autowired
    private FollowRepository repository;

    private void follow() {}
    private void unfollow() {}
}
