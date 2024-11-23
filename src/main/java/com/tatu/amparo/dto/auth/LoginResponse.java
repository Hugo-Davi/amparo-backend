package com.tatu.amparo.dto.auth;

public record LoginResponse(String accessToken, Long expiresIn) {
}
