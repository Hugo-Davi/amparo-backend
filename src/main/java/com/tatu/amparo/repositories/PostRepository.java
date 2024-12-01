package com.tatu.amparo.repositories;

import com.tatu.amparo.models.fields.Comment;
import com.tatu.amparo.models.collections.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends MongoRepository<Post, String> {

    @Query(value = "{ id: ?0 }")
    // @Update(pipeline = {"{ '$set': { comments : [?1, ...] } }"})
    @Update(value = "{ '$push': { comments : ?1 } }")
    void addComment(String id, Comment comment);

    @Query(value = "{ creator : ?0 }")
    Optional<List<Post>> getPostByCreator(String id);
}
