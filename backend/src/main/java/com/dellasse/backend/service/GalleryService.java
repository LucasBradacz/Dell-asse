package com.dellasse.backend.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dellasse.backend.contracts.gallery.GalleryCreateRequest;
import com.dellasse.backend.exceptions.DomainError;
import com.dellasse.backend.exceptions.DomainException;
import com.dellasse.backend.mappers.GalleryMapper;
import com.dellasse.backend.mappers.ImageMapper;
import com.dellasse.backend.models.Enterprise;
import com.dellasse.backend.models.Gallery;
import com.dellasse.backend.models.Image;
import com.dellasse.backend.repositories.GalleryRepository;
import com.dellasse.backend.repositories.ImageRepository;
import com.dellasse.backend.util.ConvertString;

import jakarta.persistence.EntityManager;


@Service
public class GalleryService {

    @Autowired
    private GalleryRepository galleryRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EntityManager entityManager;

    public void create(GalleryCreateRequest request, String token) {
        UUID userId = ConvertString.toUUID(token);
        if (userId == null) {
            throw new IllegalArgumentException("Invalid token");
        }
        UUID enterpriseId = userService.validateUserEnterprise(userId);
         if (enterpriseId == null) {
            throw new DomainException(DomainError.USER_NOT_FOUND_ENTERPRISE);
        }

        Gallery gallery = GalleryMapper.toEntity(request);
        gallery.setEnterprise(entityManager.find(Enterprise.class, enterpriseId));
        galleryRepository.save(gallery);
        
        List<Image> images = request.imageUrl().stream()
            .map(dto -> ImageMapper.toEntity(dto, gallery))
            .collect(Collectors.toList());

        imageRepository.saveAll(images);
    }
}
