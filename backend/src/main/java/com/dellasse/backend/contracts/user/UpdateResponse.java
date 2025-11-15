package com.dellasse.backend.contracts.user;

public record UpdateResponse(
    String name,
    String email,
    String username,
    String password,
    boolean isActive
){}
