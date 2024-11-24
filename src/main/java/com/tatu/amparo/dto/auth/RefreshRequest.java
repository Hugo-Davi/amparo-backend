package com.tatu.amparo.dto.auth;

public record RefreshRequest(String credential,String refreshToken) {
}
