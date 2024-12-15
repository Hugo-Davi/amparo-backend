package com.tatu.amparo.repositories;

import com.tatu.amparo.dto.follow.FollowGetByFollower;
import com.tatu.amparo.models.collections.Follow;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends MongoRepository<Follow, String> {
    @Query(value = "{ follower : ?0 }", fields = "{followed: 1, _id: 0}")
    Optional<List<FollowGetByFollower>> getByFollower(@Param("followerId") String followerId);

    @ExistsQuery(value = "{ follower : ?0, followed : ?1 }")
    Boolean getFollow(@Param("followerId") String followerId,
                               @Param("followedId") String followedId);

    @DeleteQuery(value = "{ follower: ?0, followed: ?1 }")
    void deleteFollow(@Param("followerId") String followerId,
                      @Param("followedId") String followedId);
}