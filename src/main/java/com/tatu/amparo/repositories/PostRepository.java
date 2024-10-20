package com.tatu.amparo.repositories;

import com.tatu.amparo.models.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, String> {
}
