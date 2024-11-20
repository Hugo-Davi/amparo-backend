package com.tatu.amparo.repositories;

import com.tatu.amparo.dto.user.SupportNetworkGet;
import com.tatu.amparo.models.SupportNetwork;
import com.tatu.amparo.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    @Query("{ 'name' : ?0 }")
    List<User> findByUsername(String username);

    @Query("{ 'email' : ?0 }")
    User findByEmail(String email);

    @Query("{ 'phoneNumber' : ?0 }")
    User findByPhoneNumber(String phoneNumber);

    @Query(value = "{ '_id' : ?0 }", fields = "{ 'supportNetwork' : 1 }")
     SupportNetworkGet getSupportNetwork(String id);
}
