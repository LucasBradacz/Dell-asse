package com.dellasse.backend.contracts.user;

public record UserUpdateRequest(
    String name,
    String email,
    String username,
    String password,
    String confirmPassword,
    boolean active
) {}
