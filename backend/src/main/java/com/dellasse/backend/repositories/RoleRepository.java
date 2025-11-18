package com.dellasse.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dellasse.backend.models.Role;

/*
 * @author Equipe Compilador 
 * @version 1.0
 * Repositório para a entidade Role, responsável por operações de persistência relacionadas a papéis.
 * - Contém método para encontrar um papel pelo nome.
 * - Utiliza Long como identificador único.
 * - Extende JpaRepository para herdar métodos padrão de CRUD.
 * - Interface anotada com @Repository para indicar que é um componente de repositório do Spring.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
