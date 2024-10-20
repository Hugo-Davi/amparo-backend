package com.tatu.amparo.controllers.dto;

public record LoginResponse(String accessToken, Long expiresIn) {
}
