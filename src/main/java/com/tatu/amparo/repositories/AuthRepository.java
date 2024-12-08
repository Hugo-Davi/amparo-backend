package com.tatu.amparo.repositories;

import com.tatu.amparo.models.collections.Authentication;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AuthRepository extends MongoRepository<Authentication, String> {
    @Query("{ 'user' : ?0 }")
    Optional<Authentication> findByUser(@Param("user") String user);
}
