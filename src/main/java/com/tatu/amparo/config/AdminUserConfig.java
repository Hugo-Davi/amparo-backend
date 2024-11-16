package com.tatu.amparo.config;

import com.tatu.amparo.models.User;
import com.tatu.amparo.models.enums.Role;
import com.tatu.amparo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Set;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception{
        if (userService.existCredential("admin@admin.com")) {
            System.out.println("Admin ja existe!");
        }
        else {
            User user = new User();
            user.setEmail("admin@admin.com");
            user.setPassword("123");
            userService.saveAdmin(user);
            System.out.println("Usuário admin criado.");
        }
    }
}
