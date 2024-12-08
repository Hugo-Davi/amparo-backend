package com.tatu.amparo.repositories;

import com.tatu.amparo.models.collections.RefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {
    @Query("{ 'token' : ?0 , 'user' : ?1 }")
    RefreshToken findByToken(@Param("token") String token,
                             @Param("userId") String userId);
}

