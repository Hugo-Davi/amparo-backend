package com.tatu.amparo.dto.institute;

import com.tatu.amparo.models.fields.Location;

public record UpdateInstituteRequest(String name, String description, Location location) {
}
