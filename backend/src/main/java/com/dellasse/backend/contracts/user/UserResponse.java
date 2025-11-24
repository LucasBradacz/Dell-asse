package com.dellasse.backend.contracts.user;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.dellasse.backend.models.Role;

public record UserResponse(
    UUID id,
    String name,
    String email,
    String username,
    String phone,
    LocalDate birthday,
    boolean active,
    List<Role> roles
) {}