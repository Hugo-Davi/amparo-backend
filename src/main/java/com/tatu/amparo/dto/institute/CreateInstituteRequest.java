package com.tatu.amparo.dto.institute;

import com.tatu.amparo.models.fields.Location;

public record CreateInstituteRequest(String name, String description, Location location) {
}
