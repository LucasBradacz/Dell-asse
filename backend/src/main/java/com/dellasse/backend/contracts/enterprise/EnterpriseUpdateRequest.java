package com.dellasse.backend.contracts.enterprise;


public record EnterpriseUpdateRequest(
    String name,
    String document,
    String address,
    String phoneNumber,
    String email,
    String urlImage
) {}