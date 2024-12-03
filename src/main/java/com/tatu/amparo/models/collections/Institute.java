package com.tatu.amparo.models.collections;

import com.tatu.amparo.dto.institute.CreateInstituteRequest;
import com.tatu.amparo.models.fields.Location;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

@Setter
@Getter
@Document(collection = "institutes")
public class Institute {
    @Id
    private String id;
    private String name;
    private String description;
    @DBRef
    private List<User> admins;
    @GeoSpatialIndexed
    private Location location;

    public boolean hasAdmin(String userId) {
        for (User admin : admins) {
            if (Objects.equals(admin.getId(), userId)) {
                return true;
            }
        }
        return false;
    }
}
