package com.tatu.amparo.services;

import com.tatu.amparo.models.User;
import com.tatu.amparo.models.enums.Role;
import com.tatu.amparo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public User save(User user) {
        user.setRoles(Set.of(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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

    public void saveAdmin(User user) {
        user.setRoles(Set.of(Role.USER, Role.ADMIN));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
    }



}
