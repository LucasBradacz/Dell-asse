package com.dellasse.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dellasse.backend.contracts.gallery.GalleryCreateRequest;
import com.dellasse.backend.service.GalleryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("gallery")
public class GalleryController {

    @Autowired
    private GalleryService galleryService;

    @PostMapping("/create")
    public void create(@Valid @RequestBody GalleryCreateRequest request, JwtAuthenticationToken token) {
        galleryService.create(request, token.getName());
    }
}
