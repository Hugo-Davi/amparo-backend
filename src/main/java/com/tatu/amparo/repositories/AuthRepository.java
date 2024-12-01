package com.tatu.amparo.repositories;

import com.tatu.amparo.models.collections.Authentication;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface AuthRepository extends MongoRepository<Authentication, String> {
    @Query("{ 'user' : ?0 }")
    Authentication findByUser(String user);
}
