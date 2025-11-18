package com.dellasse.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dellasse.backend.contracts.product.ProductCreateRequest;
import com.dellasse.backend.contracts.product.ProductUpdateRequest;
import com.dellasse.backend.contracts.product.UpdateResponse;
import com.dellasse.backend.service.ProductService;

import jakarta.validation.Valid;

/**
 * @author Equipe Compilador 
 * @version 1.0
 * Controlador para gerenciar operações relacionadas a produtos.
 * - As operações incluem criação e atualização de produtos.
 */
@RestController
@RequestMapping("product")
public class ProductContoller {
    
    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody ProductCreateRequest createRequest, JwtAuthenticationToken token){
        return productService.create(createRequest, token.getName());
    }

    @PatchMapping("/update/{id}")
    public UpdateResponse update(@Valid @RequestBody ProductUpdateRequest updateRequest, @PathVariable Long id, JwtAuthenticationToken token){
        return productService.update(updateRequest, id, token.getName());
    }
}
