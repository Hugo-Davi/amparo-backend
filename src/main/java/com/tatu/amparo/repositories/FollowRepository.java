package com.tatu.amparo.repositories;

import com.tatu.amparo.models.collections.Follow;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FollowRepository extends MongoRepository<Follow, String> {
    @DeleteQuery(value = "{ follower: ?0, followed: ?1 }")
    void deleteFollow(@Param("followerId") String followerId,
                      @Param("followedId") String followedId);
}
