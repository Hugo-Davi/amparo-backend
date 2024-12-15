package com.tatu.amparo.repositories;

import com.tatu.amparo.models.fields.Comment;
import com.tatu.amparo.models.collections.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends MongoRepository<Post, String> {

    @Query(value = "{ id: ?0 }")
    @Update(value = "{ '$push': { comments : ?1 } }")
    void addComment(@Param("id") String id,
                    @Param("comment") Comment comment);

    @Query(value = "{ creator : ?0 }")
    Optional<List<Post>> getPostByCreator(@Param("id") String id);

    @Query(value = "[{ creator : { $in : ?0 } }," +
                    " { _id: 0, creator : 1}]")
    Optional<List<Post>> getPostByCreators(@Param("creators") List<String> creators);
}
