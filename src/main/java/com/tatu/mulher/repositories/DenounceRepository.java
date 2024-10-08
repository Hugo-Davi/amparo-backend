package com.tatu.mulher.repositories;

import com.tatu.mulher.models.Denounce;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DenounceRepository extends MongoRepository<Denounce, String> {
}
