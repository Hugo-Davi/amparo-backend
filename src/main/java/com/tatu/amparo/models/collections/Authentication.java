package com.tatu.amparo.models.collections;

import com.tatu.amparo.dto.auth.LoginRequest;
import com.tatu.amparo.models.enums.Role;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Document(collection = "authentications")
public class Authentication {
    @DBRef
    private User user;
    private String password;
    private Set<Role> roles;

    public Authentication() {}

    public Authentication(User user, String password, Set<Role> roles) {
        this.user = user;
        this.password = password;
        this.roles = roles;
    }

    public boolean isLoginCorrect(LoginRequest loginRequest, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(loginRequest.password(), this.password);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role newRole) {
        this.roles.add(newRole);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
