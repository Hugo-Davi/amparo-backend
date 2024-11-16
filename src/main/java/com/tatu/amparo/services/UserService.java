package com.tatu.amparo.services;

import com.tatu.amparo.dto.RegisterRequest;
import com.tatu.amparo.models.User;
import com.tatu.amparo.models.enums.Role;
import com.tatu.amparo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

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

    public User registerUser(RegisterRequest registerRequest) {
        User user = new User();
        if (Pattern.compile("^(.*)@(.*)\\.com$", Pattern.CASE_INSENSITIVE).matcher(registerRequest.credential()).matches()) {
            user.setEmail(registerRequest.credential());
        } else { user.setPhoneNumber(registerRequest.credential()); }

        user.setRoles(Set.of(Role.USER));
        user.setPassword(passwordEncoder.encode(registerRequest.password()));
        return repository.save(user);
    }

    public boolean existCredential(String credential) {
        // Credential is a email?
        if (Pattern.compile("^(.*)@(.*)\\.com$", Pattern.CASE_INSENSITIVE).matcher(credential).matches()){
            // Credential exist in email?
            if (repository.findByEmail(credential) == null) {
                return false;
            }
            return true;
        }
        // Credential is a Phone Number?
        if (Pattern.compile("^\\d+$", Pattern.CASE_INSENSITIVE).matcher(credential).matches()) {
            // Credential exist in phone number?
            if (repository.findByPhoneNumber(credential) == null) {
                return false;
            }
            return true;
        }
        // Invalid credential
        return true;
    }

    public User findByCredential(String credential) {
        if (Pattern.compile("^(.*)@(.*)\\.com$", Pattern.CASE_INSENSITIVE).matcher(credential).matches()){
            return repository.findByEmail(credential);
        }
        return repository.findByPhoneNumber(credential);
    }
}
