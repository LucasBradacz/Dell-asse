package com.dellasse.backend.contracts.gallery;

import java.util.List;

public record GalleryCreateRequest(String name, String description, List<String> imageUrl) {
}