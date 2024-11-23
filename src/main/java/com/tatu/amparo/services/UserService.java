package com.tatu.amparo.services;

import com.tatu.amparo.models.User;
import com.tatu.amparo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();



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
