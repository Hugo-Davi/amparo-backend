package com.tatu.amparo.repositories;

import com.tatu.amparo.models.Denounce;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DenounceRepository extends MongoRepository<Denounce, String> {
}
