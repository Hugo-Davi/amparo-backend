package com.tatu.amparo.services;

import com.tatu.amparo.dto.user.UserCredentials;
import com.tatu.amparo.dto.user.UserHeader;
import com.tatu.amparo.models.collections.User;
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
    public void updateUserHeader(String id, UserHeader userHeader) {
        repository.updateUserHeader(id, userHeader.name(),
                                        userHeader.description(),
                                        userHeader.age(),
                                        userHeader.address());
    }
    public void updateUserCredentials(String id, UserCredentials userCredentials) {
        repository.updateUserCredentials(id, userCredentials.cpf(),
                                             userCredentials.phoneNumber(),
                                             userCredentials.email());
    }
}
