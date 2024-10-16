package com.tatu.mulher.services;

import com.tatu.mulher.controllers.dto.LoginRequest;
import com.tatu.mulher.models.User;
import com.tatu.mulher.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User save(User user) {
        return repository.save(user);
    }
    public User get(String id) {
        return repository.findById(id).orElse(null);
    }
    public List<User> getAll(){
        return repository.findAll();
    }
    public void delete(String id) {
        repository.deleteById(id);
    }
    public User update(User user) {
        return repository.save(user);
    }
    public boolean existById(String id) { return repository.existsById(id); }
    public List<User> findByUsername(String username) { return repository.findByUsername(username); }



}
