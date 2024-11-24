package com.tatu.amparo.models;

import com.tatu.amparo.models.enums.Role;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Getter
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String name;

    private String email;
    private String phoneNumber;

    private Set<Role> roles;

    private String age;
    private String cpf;
    private Address address;

    private List<SupportNetwork> supportNetwork;

    public User() {}

    public User(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role newRole) {
        this.roles.add(newRole);
    }

    public void setSupportNetwork(List<SupportNetwork> supportNetwork) {
        this.supportNetwork = supportNetwork;
    }
}
