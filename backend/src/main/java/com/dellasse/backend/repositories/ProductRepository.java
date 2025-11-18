package com.dellasse.backend.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dellasse.backend.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
        boolean existsByIdAndEnterprise_Id(Long id, UUID enterpriseId);
}
