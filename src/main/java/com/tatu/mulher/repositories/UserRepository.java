package com.tatu.mulher.repositories;

import com.tatu.mulher.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

}
