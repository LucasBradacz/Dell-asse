package com.dellasse.backend.contracts.enterprise;


public record UpdateRequest(
    String name,
    String document,
    String address,
    String phoneNumber,
    String email,
    String urlImage
) {}