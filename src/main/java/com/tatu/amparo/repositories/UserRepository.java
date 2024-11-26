package com.tatu.amparo.repositories;

import com.tatu.amparo.dto.user.SupportNetworkGet;
import com.tatu.amparo.models.Address;
import com.tatu.amparo.models.SupportNetwork;
import com.tatu.amparo.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
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

    @Query(value = "{ '_id' : ?0 }")
    @Update(pipeline = {"{ '$set' : { name : ?1, description : ?2, age : ?3, address : ?4  } }"})
    void updateUserHeader(String id, String name, String description, String age, Address address);

    @Query(value = "{ '_id' : ?0 }")
    @Update(pipeline = {"{ '$set' : { cpf : ?1, phoneNumber : ?2, email : ?3  } }"})
    void updateUserCredentials(String id, String cpf, String phoneNumber, String email);

    @Query(value = "{ '_id' : ?0 }", fields = "{ 'supportNetwork' : 1 }")
     SupportNetworkGet getSupportNetwork(String id);

    @Query(value = "{ '_id' : ?0 }", fields = "{ 'supportNetwork' : 1 }")
    @Update(pipeline = {"{ '$set' supportNetworks : ?1 }"})
    void updateSupportNetwork(String id, List<SupportNetwork> supportNetworks);
}
