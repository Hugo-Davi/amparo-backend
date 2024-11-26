package com.tatu.amparo.models;

import com.tatu.amparo.models.enums.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String name;

    private String description;

    private String email;
    private String phoneNumber;

    private String age;
    private String cpf;
    private Address address;

    private List<SupportNetwork> supportNetwork;

    public User() {}

    public User(String id) {
        this.id = id;
    }

}
