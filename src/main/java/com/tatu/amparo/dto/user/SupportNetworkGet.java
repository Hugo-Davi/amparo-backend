package com.tatu.amparo.dto.user;

import com.tatu.amparo.models.fields.SupportNetwork;

import java.util.List;

public record SupportNetworkGet(
    String id,
    List<SupportNetwork> supportNetwork
) {
}
