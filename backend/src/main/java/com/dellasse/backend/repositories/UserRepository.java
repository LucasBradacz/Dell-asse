package com.dellasse.backend.repositories;
import  com.dellasse.backend.models.User;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;  

@Repository
public interface UserRepository extends JpaRepository<User, UUID>  {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<User> findByUsername(String username);
    boolean existsByUuidAndRoles(UUID id, Long role);
}
