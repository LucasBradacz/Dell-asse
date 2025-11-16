package com.dellasse.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.dellasse.backend.contracts.product.CreateRequest;

import jakarta.validation.Valid;

public class PartyController {

    @Autowired
    private PartyService partyService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody CreateRequest createRequest, JwtAuthenticationToken token){
        return partyService.create(createRequest, token.getName());
    }
}
