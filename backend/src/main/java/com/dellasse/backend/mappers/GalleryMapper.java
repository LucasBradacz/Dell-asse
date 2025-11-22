package com.dellasse.backend.mappers;

import java.util.ArrayList;
import java.util.List;

import com.dellasse.backend.contracts.gallery.GalleryCreateRequest;
import com.dellasse.backend.contracts.gallery.GalleryResponse;
import com.dellasse.backend.contracts.image.ImageCreateRequest;
import com.dellasse.backend.contracts.party.PartyResponse;
import com.dellasse.backend.contracts.product.ProductResponse;
import com.dellasse.backend.models.Gallery;
import com.dellasse.backend.models.Image;
import com.dellasse.backend.models.Party;
import com.dellasse.backend.models.Product;

public class GalleryMapper {
    

    public static Gallery toEntity(GalleryCreateRequest request) {
        Gallery gallery = new Gallery();
        gallery.setName(request.name());
        return gallery;
    }

    public static GalleryResponse toResponse(Gallery gallery) {
        List<ImageCreateRequest> images = new ArrayList<>();
        if (gallery.getImages() != null) {
            for (Image img : gallery.getImages()) {
                images.add(new ImageCreateRequest(img.getUrl(), img.getAlt()));
            }
        }
        
        List<PartyResponse> partys = new ArrayList<>();
        if (gallery.getPartys() != null) {
        for (Party party : gallery.getPartys()) {
            List<ProductResponse> products = new ArrayList<>();
            if (party.getProducts() != null) {
                for (Product p : party.getProducts()) {
                    products.add(new ProductResponse(
                        p.getId(), p.getName(), p.getDescription(), p.getPrice(), p.getStockQuantity(), p.getCategory(), p.getImageUrl()
                    ));
                }
            }
            partys.add(new PartyResponse(
                party.getId(),
                party.getTitle(),
                party.getObservations(),
                party.getGenerateBudget() != null ? String.valueOf(party.getGenerateBudget()) : null,
                party.getImgExample(),
                party.getStatus(),
                products
            ));
        }
        }
        return new GalleryResponse(
            gallery.getId(),
            gallery.getName(),
            images,
            partys
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
