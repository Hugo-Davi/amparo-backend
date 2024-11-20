package com.tatu.amparo.repositories;

import com.tatu.amparo.models.Authentication;
import com.tatu.amparo.models.Denounce;
import com.tatu.amparo.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface AuthRepository extends MongoRepository<Authentication, String> {
    @Query("{ 'user' : ?0 }")
    Authentication findByUser(String user);
}
