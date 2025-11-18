package com.dellasse.backend.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dellasse.backend.models.Product;

/*
 * @author Equipe Compilador 
 * @version 1.0
 * Repositório para a entidade Product, responsável por operações de persistência relacionadas a produtos.
 * - Contém método para verificar a existência de um produto pelo ID e ID da empresa.
 * - Utiliza Long como identificador único.
 * - Extende JpaRepository para herdar métodos padrão de CRUD.
 * - Interface anotada com @Repository para indicar que é um componente de repositório do Spring.
 */
public interface ProductRepository extends JpaRepository<Product, Long>{
        boolean existsByIdAndEnterprise_Id(Long id, UUID enterpriseId);
}
