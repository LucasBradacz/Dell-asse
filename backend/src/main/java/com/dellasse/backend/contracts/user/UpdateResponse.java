package com.dellasse.backend.contracts.user;

public record UpdateResponse(
    String name,
    String email,
    String username,
    boolean isActive
){}
