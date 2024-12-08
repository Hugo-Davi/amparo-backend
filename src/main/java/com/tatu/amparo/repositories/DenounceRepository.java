package com.tatu.amparo.repositories;

import com.tatu.amparo.models.collections.Denounce;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DenounceRepository extends MongoRepository<Denounce, String> {
    @Query("{ 'user' : ?0 }")
    List<Denounce> findByUser(@Param("user") String user);
}
