package com.tatu.amparo.dto.user;

import com.tatu.amparo.models.Address;

public record UserHeader(String name, String description, String age, Address address) {
}
