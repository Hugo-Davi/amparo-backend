package com.tatu.amparo.repositories;

import com.tatu.amparo.models.collections.Follow;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends MongoRepository<Follow, String> {
    @Query(value = "{ followerId : ?0 }, {followedId: 1, id: 0}")
    Optional<List<String>> getByFollower(@Param("followerId") String followerId);

    @DeleteQuery(value = "{ follower: ?0, followed: ?1 }")
    void deleteFollow(@Param("followerId") String followerId,
                      @Param("followedId") String followedId);
}
