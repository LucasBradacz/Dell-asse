package com.dellasse.backend.mappers;

import com.dellasse.backend.contracts.image.ImageCreateRequest;
import com.dellasse.backend.models.Gallery;
import com.dellasse.backend.models.Image;

public class ImageMapper {

    public static Image toEntity(ImageCreateRequest request, Gallery gallery) {
        return new Image(
            request.url(),
            request.alt(),
            gallery
        );
    }
}
