package com.dellasse.backend.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dellasse.backend.models.Enterprise;

/*
 * @author Equipe Compilador 
 * @version 1.0
 * Repositório para a entidade Enterprise, responsável por operações de persistência relacionadas a empresas.
 * - Contém método para verificar a existência de uma empresa pelo nome ou documento.
 * - Utiliza UUID como identificador único.
 * - Extende JpaRepository para herdar métodos padrão de CRUD.
 * - Interface anotada com @Repository para indicar que é um componente de repositório do Spring.
 */
@Repository
public interface EnterpriseRepository extends JpaRepository<Enterprise, UUID> {
    boolean existsByNameOrDocument(String name, String document);
}
