package com.tatu.amparo.services.auth;

import com.tatu.amparo.models.collections.RefreshToken;
import com.tatu.amparo.models.collections.User;
import com.tatu.amparo.repositories.RefreshTokenRepository;
import com.tatu.amparo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RefreshTokenService {
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public String generateRefreshToken(String userId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setUser(new User(userId));

        return refreshTokenRepository.save(refreshToken).getToken();
    }

    public RefreshToken getRefreshToken(String token, String userId) {
        return refreshTokenRepository.findByToken(token, userId);
    }
}
