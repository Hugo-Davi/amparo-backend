package com.tatu.amparo.models.collections;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document(collection = "refreshTokens")
public class RefreshToken {
    //@Indexed(name="refreshToken", expireAfterSeconds=10)
    @Id
    String id;
    String token;
    @DBRef
    User user;
}
