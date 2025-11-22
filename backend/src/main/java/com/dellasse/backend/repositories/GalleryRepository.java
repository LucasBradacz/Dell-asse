package com.dellasse.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dellasse.backend.models.Gallery;

public interface GalleryRepository extends JpaRepository<Gallery, Long>{

    @Query("select g from Gallery g")
    List<Gallery> findAllWithRelations();
}
