package com.dellasse.backend.repositories;
import  com.dellasse.backend.models.User;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;  

/*
 * @author Equipe Compilador 
 * @version 1.0
 * Repositório para a entidade User, responsável por operações de persistência relacionadas a usuários.
 * - Contém métodos para verificar a existência de usuários por nome de usuário, email, UUID e papéis ou empresa associada.
 * - Utiliza UUID como identificador único.
 * - Extende JpaRepository para herdar métodos padrão de CRUD.
 * - Interface anotada com @Repository para indicar que é um componente de repositório do Spring.
 * - Inclui uma consulta personalizada para obter o ID da empresa associada a um usuário pelo seu UUID.
 *      Métodos:
 *  -existsByUsername: Verifica se um usuário existe pelo nome de usuário.
 *  -existsByEmail: Verifica se um usuário existe pelo email.
 *  -findByUsername: Encontra um usuário pelo nome de usuário.
 *  -existsByUuidAndRoles_Id: Verifica se um usuário com um UUID específico possui um papel específico.
 *  -existsByUuidAndEnterprise_Id: Verifica se um usuário com um UUID específico está associado a uma empresa específica.
 *  -findEnterpriseIdByUuid: Obtém o ID da empresa associada a um usuário pelo seu UUID.
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID>  {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<User> findByUsername(String username);
    boolean existsByUuidAndRoles_Id(UUID id, Long role);
    boolean existsByUuidAndEnterprise_Id(UUID userId, UUID enterpriseId);
 
    @Query("SELECT u.enterprise.id FROM User u WHERE u.uuid = :uuid")
    Optional<UUID> findEnterpriseIdByUuid(UUID uuid);
}
