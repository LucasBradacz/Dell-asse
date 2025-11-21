package com.dellasse.backend.contracts.gallery;

import java.util.List;

import com.dellasse.backend.contracts.image.ImageCreateRequest;

public record GalleryCreateRequest(String name, String description, List<ImageCreateRequest> imageUrl) {
}