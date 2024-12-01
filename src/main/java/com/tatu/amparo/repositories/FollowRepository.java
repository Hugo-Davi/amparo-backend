package com.tatu.amparo.repositories;

import com.tatu.amparo.models.collections.Follow;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FollowRepository extends MongoRepository<Follow, String> {
}
