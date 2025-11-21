package com.dellasse.backend.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.dellasse.backend.contracts.gallery.GalleryCreateRequest;
import com.dellasse.backend.contracts.gallery.GalleryResponse;
import com.dellasse.backend.contracts.image.ImageCreateRequest;
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

    public static GalleryResponse toResponse(Gallery gallery) {
        java.util.List<ImageCreateRequest> images = new java.util.ArrayList<>();
        if (gallery.getImages() != null) {
            for (Image img : gallery.getImages()) {
                images.add(new ImageCreateRequest(img.getUrl(), img.getAlt()));
            }
        }
        return new GalleryResponse(
            gallery.getName(),
            gallery.getDescription(),
            gallery.getName(),
            images
        );
    }

    public static List<GalleryResponse> toResponseList(List<Gallery> galleries) {
        List<GalleryResponse> list = new ArrayList<>();
        if (galleries != null) {
            for (Gallery gallery : galleries) {
                list.add(toResponse(gallery));
            }
        }
        return list;
    }
}
