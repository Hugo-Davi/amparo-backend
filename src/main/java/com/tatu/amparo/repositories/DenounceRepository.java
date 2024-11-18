package com.tatu.amparo.repositories;

import com.tatu.amparo.models.Denounce;
import com.tatu.amparo.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface DenounceRepository extends MongoRepository<Denounce, String> {
    @Query("{ 'user' : ?0 }")
    List<Denounce> findByUser(String user);
}
