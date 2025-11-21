package com.dellasse.backend.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.dellasse.backend.contracts.gallery.GalleryCreateRequest;
import com.dellasse.backend.models.Gallery;
import com.dellasse.backend.models.Image;

public class GalleryMapper {
    

    public static Gallery toEntity(GalleryCreateRequest request) {
        Gallery gallery = new Gallery();
        gallery.setName(request.name());
        gallery.setDescription(request.description());
        
        if (request.imageUrl() != null) {
            List<Image> images = request.imageUrl().stream()
                .map(dto -> new Image(dto.url(), dto.alt(), gallery))
                .collect(Collectors.toList());
            gallery.setImages(images);
        }
        
        return gallery;
    }
}
