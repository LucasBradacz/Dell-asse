package com.dellasse.backend.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dellasse.backend.contracts.enterprise.CreateRequest;
import com.dellasse.backend.contracts.enterprise.UpdateRequest;
import com.dellasse.backend.service.EnterpriseService;

import jakarta.validation.Valid;

/**
 * @author Equipe Compilador 
 * @version 1.0
 * Controlador para gerenciar operações relacionadas a empresas.
 * - As operações incluem criação e atualização de empresas.
 * - As operações são protegidas e requerem que o usuário tenha a função ADMIN.
 * - Utiliza o serviço EnterpriseService para realizar a lógica de negócio.
 */
@RequestMapping("enterprise")
public class EnterpriseController {
    
    @Autowired
    private EnterpriseService enterpriseService;
    
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@Valid CreateRequest request, JwtAuthenticationToken token) {
        return enterpriseService.create(request, token.getName()); 
    }

    @PostMapping("/update/{enterpriseId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@Valid UpdateRequest request, @PathVariable UUID enterpriseId, JwtAuthenticationToken token) {
        return enterpriseService.update(request, enterpriseId, token.getName());
    }
}
