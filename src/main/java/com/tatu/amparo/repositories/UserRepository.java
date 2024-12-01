package com.tatu.amparo.repositories;

import com.tatu.amparo.dto.user.SupportNetworkGet;
import com.tatu.amparo.models.fields.Address;
import com.tatu.amparo.models.fields.SupportNetwork;
import com.tatu.amparo.models.collections.User;
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

//    @Query(value = "{ '_id' : ?0 }")
//    @Update(pipeline = {"{ '$set' : { " +
//            "name : {$cond:{if:{$ne: [?1, '']}, then: ?1, else: '$name'}}" +
//            "description : {$cond:{if:{$ne: [?2, '']}, then: ?2, else: '$description'}}" +
//            "age : {$cond:{if:{$ne: [?3, '']}, then: ?3, else: '$age'}}" +
//            "address : {$cond:{if:{$ne: [?4, '']}, then: ?4, else: '$address'}}" +
//            "cpf : {$cond:{if:{$ne: [?5, '']}, then: ?5, else: '$name'}}" +
//            "phoneNumber : {$cond:{if:{$ne: [?6, '']}, then: ?6, else: '$phoneNumber'}}" +
//            "email : {$cond:{if:{$ne: [?7, '']}, then: ?7, else: '$email'}}" +
//            "} }"})
//    void updateUser(String id,
//                    String name, String description, String age, Address address,
//                    String cpf, String phoneNumber, String email);

    @Query(value = "{ '_id' : ?0 }")
    @Update(pipeline = {"{ '$set' : { " +
                            "name : {$cond:{if:{$ne: [?1, '']}, then: ?1, else: '$name'}}" +
                            "description : {$cond:{if:{$ne: [?2, '']}, then: ?2, else: '$description'}}" +
                            "age : {$cond:{if:{$ne: [?3, '']}, then: ?3, else: '$age'}}" +
                            "address : {$cond:{if:{$ne: [?4, '']}, then: ?4, else: '$address'}}" +
                        "} }"})
    void updateUserHeader(String id, String name, String description, String age, Address address);

    @Query(value = "{ '_id' : ?0 }")
    @Update(pipeline = {"{ '$set' : { " +
                            "cpf : {$cond:{if:{$ne: [?1, '']}, then: ?1, else: '$name'}}" +
                            "phoneNumber : {$cond:{if:{$ne: [?2, '']}, then: ?2, else: '$phoneNumber'}}" +
                            "email : {$cond:{if:{$ne: [?3, '']}, then: ?3, else: '$email'}}" +
                            "} }"})
    void updateUserCredentials(String id, String cpf, String phoneNumber, String email);

    @Query(value = "{ '_id' : ?0 }", fields = "{ 'supportNetwork' : 1 }")
     SupportNetworkGet getSupportNetwork(String id);

    @Query(value = "{ '_id' : ?0 }", fields = "{ 'supportNetwork' : 1 }")
    @Update(pipeline = {"{ '$set': { supportNetwork : ?1 } }"})
    void updateSupportNetwork(String id, List<SupportNetwork> supportNetwork);
}
