package com.tatu.mulher.repositories;

import com.tatu.mulher.models.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, String> {

}
