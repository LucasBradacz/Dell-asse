package com.dellasse.backend.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dellasse.backend.contracts.enterprise.EnterpriseCreateRequest;
import com.dellasse.backend.contracts.enterprise.EnterpriseResponse;
import com.dellasse.backend.contracts.enterprise.EnterpriseUpdateRequest;
import com.dellasse.backend.service.EnterpriseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("enterprise")
public class EnterpriseController {
    
    @Autowired
    private EnterpriseService enterpriseService;
    
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@Valid EnterpriseCreateRequest request, JwtAuthenticationToken token) {
        return enterpriseService.create(request, token.getName()); 
    }

    @PostMapping("/update/{enterpriseId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@Valid EnterpriseUpdateRequest request, @PathVariable UUID enterpriseId, JwtAuthenticationToken token) {
        return enterpriseService.update(request, enterpriseId, token.getName());
    }

    @GetMapping("/{enterpriseId}")
    @PreAuthorize("hasRole('FUNCIONARIO') or hasRole('ADMIN')")
    public EnterpriseResponse findById(@PathVariable UUID enterpriseId, JwtAuthenticationToken token) {
        return enterpriseService.findById(enterpriseId, token.getName());
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<EnterpriseResponse> findAll(JwtAuthenticationToken token){
        return enterpriseService.findAll(token.getName());
    }
}
