package com.dellasse.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dellasse.backend.models.Gallery;

public interface GalleryRepository extends JpaRepository<Gallery, Long>{
    
}
