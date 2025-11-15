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

import com.dellasse.backend.contracts.user.CreateRequest;
import com.dellasse.backend.contracts.user.LoginRequest;
import com.dellasse.backend.contracts.user.UpdateRequest;
import com.dellasse.backend.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody @Valid CreateRequest request) {
        return userService.createUser(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody @Valid LoginRequest loginRequest) {
        return userService.loginUser(loginRequest);
    }

    @PostMapping("/update/{userId}")
    public ResponseEntity<?> updateUser(@RequestBody @Valid UpdateRequest request, @PathVariable UUID userId, JwtAuthenticationToken token) {
        return userService.updateUser(request, userId, token.getName());
    }
}
