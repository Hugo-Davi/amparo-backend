package com.tatu.amparo.repositories;

import com.tatu.amparo.dto.user.SupportNetworkGet;
import com.tatu.amparo.models.fields.Address;
import com.tatu.amparo.models.fields.SupportNetwork;
import com.tatu.amparo.models.collections.User;
import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    @Query("{ 'name' : ?0 }")
    List<User> findByUsername(@Param("username") String username);

    @Query("{ 'email' : ?0 }")
    Optional<User> findByEmail(@Param("email") String email);

    @ExistsQuery("{ 'email' : ?0 }")
    Boolean existEmail(@Param("email") String email);

    @ExistsQuery("{ 'phoneNumber' : ?0 }")
    Boolean existPhoneNumber(@Param("phoneNumber") String phoneNumber);


    @Query("{ 'phoneNumber' : ?0 }")
    Optional<User> findByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    @Query(value = "{ '_id' : ?0 }")
    @Update(pipeline = {"{ '$set' : { " +
                            "name : {$cond:{if:{$ne: [?1, '']}, then: ?1, else: '$name'}}" +
                            "description : {$cond:{if:{$ne: [?2, '']}, then: ?2, else: '$description'}}" +
                            "age : {$cond:{if:{$ne: [?3, '']}, then: ?3, else: '$age'}}" +
                            "address : {$cond:{if:{$ne: [?4, '']}, then: ?4, else: '$address'}}" +
                        "} }"})
    void updateUserHeader(@Param("id") String id,
                          @Param("name") String name,
                          @Param("description") String description,
                          @Param("age") String age,
                          @Param("address") Address address);

    @Query(value = "{ '_id' : ?0 }")
    @Update(pipeline = {"{ '$set' : { " +
                            "cpf : {$cond:{if:{$ne: [?1, '']}, then: ?1, else: '$name'}}" +
                            "phoneNumber : {$cond:{if:{$ne: [?2, '']}, then: ?2, else: '$phoneNumber'}}" +
                            "email : {$cond:{if:{$ne: [?3, '']}, then: ?3, else: '$email'}}" +
                            "} }"})
    void updateUserCredentials(@Param("id") String id,
                               @Param("cpf") String cpf,
                               @Param("phoneNumber") String phoneNumber,
                               @Param("email") String email);

    @Query(value = "{ '_id' : ?0 }", fields = "{ 'supportNetwork' : 1 }")
     SupportNetworkGet getSupportNetwork(@Param("id") String id);

    @Query(value = "{ '_id' : ?0 }", fields = "{ 'supportNetwork' : 1 }")
    @Update(pipeline = {"{ '$set': { supportNetwork : ?1 } }"})
    void updateSupportNetwork(@Param("id") String id,
                              @Param("supportNetwork") List<SupportNetwork> supportNetwork);
}