package com.dellasse.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dellasse.backend.models.Enterprise;

@Repository
public interface EnterpriseRepository extends JpaRepository<Enterprise, String> {
    
}
