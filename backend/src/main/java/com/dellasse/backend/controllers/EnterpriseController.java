package com.dellasse.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;

import com.dellasse.backend.contracts.enterprise.CreateEnterprise;
import com.dellasse.backend.service.EnterpriseService;

import jakarta.validation.Valid;

public class EnterpriseController {
    
    @Autowired
    private EnterpriseService enterpriseService;


    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@Valid CreateEnterprise request, JwtAuthenticationToken token) {
        return enterpriseService.create(request, token.getName()); 
    }
}
