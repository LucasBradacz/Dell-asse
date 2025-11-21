package com.dellasse.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dellasse.backend.models.Image;

public interface ImageRepository extends JpaRepository<Image, Long>{
    
}
