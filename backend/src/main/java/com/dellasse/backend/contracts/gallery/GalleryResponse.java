package com.dellasse.backend.contracts.gallery;

import java.util.List;

import com.dellasse.backend.contracts.image.ImageCreateRequest;

import jakarta.validation.constraints.NotBlank;

public record GalleryResponse(
    
    @NotBlank String name,
    @NotBlank String description,
    @NotBlank String alt,
    List<ImageCreateRequest> image) {
    
}
