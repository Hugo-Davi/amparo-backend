package com.tatu.amparo.repositories;

import com.tatu.amparo.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    @Query("{ 'name' : ?0 }")
    List<User> findByUsername(String username);

    @Query("{ 'email' : ?0 }")
    User findByEmail(String email);

    @Query("{ 'phoneNumber' : ?0 }")
    User findByPhoneNumber(String phoneNumber);
}
