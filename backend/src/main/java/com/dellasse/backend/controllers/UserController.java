package com.dellasse.backend.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dellasse.backend.contracts.user.UserCreateRequest;
import com.dellasse.backend.contracts.user.UserLoginRequest;
import com.dellasse.backend.contracts.user.UserUpdateRequest;
import com.dellasse.backend.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserCreateRequest request, JwtAuthenticationToken token) {
        return userService.createUser(request, token.getName());
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody @Valid UserLoginRequest loginRequest) {
        return userService.loginUser(loginRequest);
    }

    @PostMapping("/update/{userId}")
    public ResponseEntity<?> updateUser(@RequestBody @Valid UserUpdateRequest request, @PathVariable UUID userId, JwtAuthenticationToken token) {
        return userService.updateUser(request, userId, token.getName());
    }
}
