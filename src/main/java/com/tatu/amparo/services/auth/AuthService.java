package com.tatu.amparo.services.auth;

import com.tatu.amparo.dto.auth.RegisterRequest;
import com.tatu.amparo.models.collections.Authentication;
import com.tatu.amparo.models.collections.User;
import com.tatu.amparo.models.enums.Role;
import com.tatu.amparo.repositories.AuthRepository;
import com.tatu.amparo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;
import java.util.regex.Pattern;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthRepository authRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User save(User user, String password) {
        User createdUser = userRepository.save(user);
        if (createdUser.getId() == null) {
            return null;
        }
        Authentication auth = new Authentication();
        auth.setUser(createdUser);
        auth.setRoles(Set.of(Role.USER));
        auth.setPassword(passwordEncoder.encode(password));

        authRepository.save(auth);
        return createdUser;
    }

    public void saveAdmin(User user, String password) {

        User createdUser = userRepository.save(user);
        if (createdUser.getId() == null) {
            return;
        }
        Authentication auth = new Authentication();
        auth.setUser(createdUser);
        auth.setRoles(Set.of(Role.USER, Role.ADMIN));
        auth.setPassword(passwordEncoder.encode(password));

        authRepository.save(auth);
    }

    public Authentication registerUser(RegisterRequest registerRequest) {
        User user = new User();
        if (Pattern.compile("^(.*)@(.*)\\.com$", Pattern.CASE_INSENSITIVE).matcher(registerRequest.credential()).matches()) {
            user.setEmail(registerRequest.credential());
        } else { user.setPhoneNumber(registerRequest.credential()); }
        user.setSupportNetwork(new ArrayList<>());

        // check if user was created
        User createdUser = userRepository.save(user);
        if (createdUser.getId() == null) {
            return null;
        }

        Authentication auth = new Authentication();
        auth.setUser(createdUser);
        auth.setRoles(Set.of(Role.USER));
        auth.setPassword(passwordEncoder.encode(registerRequest.password()));

        return authRepository.save(auth);
    }

    public boolean existCredential(String credential) {
        // Credential is a email?
        if (Pattern.compile("^(.*)@(.*)\\.com$", Pattern.CASE_INSENSITIVE).matcher(credential).matches()){
            // Credential exist in email?
            if (userRepository.findByEmail(credential) == null) {
                return false;
            }
            return true;
        }
        // Credential is a Phone Number?
        if (Pattern.compile("^\\d+$", Pattern.CASE_INSENSITIVE).matcher(credential).matches()) {
            // Credential exist in phone number?
            if (userRepository.findByPhoneNumber(credential) == null) {
                return false;
            }
            return true;
        }
        // Invalid credential
        return true;
    }

    public Authentication findByCredential(String credential) {
        if (Pattern.compile("^(.*)@(.*)\\.com$", Pattern.CASE_INSENSITIVE).matcher(credential).matches()){
            return authRepository.findByUser(userRepository.findByEmail(credential).getId());
        }
        return authRepository.findByUser(userRepository.findByPhoneNumber(credential).getId()) ;
    }


}
