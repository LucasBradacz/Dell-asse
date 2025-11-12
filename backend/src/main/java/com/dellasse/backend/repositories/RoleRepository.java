package com.dellasse.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dellasse.backend.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
