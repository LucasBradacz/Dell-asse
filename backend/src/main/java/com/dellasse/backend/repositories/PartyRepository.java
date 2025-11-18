package com.dellasse.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dellasse.backend.models.Party;

/*
 * @author Equipe Compilador 
 * @version 1.0
 * Repositório para a entidade Party, responsável por operações de persistência relacionadas a festas.
 * - Utiliza Long como identificador único.
 * - Extende JpaRepository para herdar métodos padrão de CRUD.
 * - Interface anotada com @Repository para indicar que é um componente de repositório do @Spring.
 */
@Repository
public interface PartyRepository extends JpaRepository<Party, Long> {

}
