package com.tatu.amparo.models.collections;

import com.tatu.amparo.models.fields.Address;
import com.tatu.amparo.models.fields.Location;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document(collection = "denounces")
public class Denounce {
    @Id
    private String id;
    @DBRef
    private User user;

    // Dados da denúncia
    private String name;
    private String description;
    private Address address;
    @GeoSpatialIndexed
    private Location location;
}
