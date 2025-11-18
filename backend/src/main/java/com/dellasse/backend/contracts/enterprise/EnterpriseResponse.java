package com.dellasse.backend.contracts.enterprise;

import java.util.UUID;

public record EnterpriseResponse(
    UUID id,
    String name,
    String document,
    String address,
    String phoneNumber,
    String email,
    String urlImage
) {}
