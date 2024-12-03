package com.tatu.amparo.repositories;

import com.tatu.amparo.models.collections.Institute;
import com.tatu.amparo.models.fields.Address;
import com.tatu.amparo.models.fields.Location;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.util.List;

public interface InstituteRepository extends MongoRepository<Institute, String> {
    @Query(value = "{ id : ?0 }")
    @Update(pipeline = {"{ '$set' : { " +
                            "name : {$cond:{if:{$ne: [?1, '']}, then: ?1, else: '$name'}}" +
                            "description : {$cond:{if:{$ne: [?2, '']}, then: ?2, else: '$description'}}" +
                            "location : {$cond:{if:{$ne: [?3, '']}, then: ?3, else: '$location'}}" +
                        "} }"})
    void updateInstitute(String id, String name, String description, Location location);

    @Query(value = "{ location: { $near: { " +
                                    "$geometry: { type : 'Point', coordinates: [?0, ?1] }, " +
                                    "$maxDistance: 10," +
                                    "$minDistance: 0," +
                                "}" +
                            "}" +
                        "}")
    List<Institute> getNear(Double longitude, Double latitude);
}
