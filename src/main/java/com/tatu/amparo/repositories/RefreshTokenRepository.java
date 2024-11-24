package com.tatu.amparo.repositories;

import com.tatu.amparo.models.RefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {
    @Query("{ 'token' : ?0 , 'user' : ?1 }")
    RefreshToken findByToken(String token, String userId);
}

