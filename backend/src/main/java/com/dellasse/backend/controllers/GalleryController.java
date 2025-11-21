package com.dellasse.backend.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dellasse.backend.contracts.gallery.GalleryCreateRequest;
import com.dellasse.backend.contracts.gallery.GalleryResponse;
import com.dellasse.backend.contracts.image.ImageCreateRequest;
import com.dellasse.backend.models.Gallery;
import com.dellasse.backend.models.Image;
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

    @GetMapping("/all")
    public List<GalleryResponse> getAll(JwtAuthenticationToken token) {
        return galleryService.findAll(token.getName());
    }
}
