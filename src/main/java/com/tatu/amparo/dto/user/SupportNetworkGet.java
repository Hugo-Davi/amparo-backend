package com.tatu.amparo.dto.user;

import com.tatu.amparo.models.SupportNetwork;

import java.util.List;

public record SupportNetworkGet(
    String id,
    List<SupportNetwork> supportNetwork
) {
}
