package com.tatu.amparo.models.collections;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "follows")
public class Follow {
    private String follower;
    private String followed;
}
