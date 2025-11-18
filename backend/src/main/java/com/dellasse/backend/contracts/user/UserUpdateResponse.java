package com.dellasse.backend.contracts.user;

public record UserUpdateResponse(
    String name,
    String email,
    String username,
    boolean isActive
){}
