package com.dellasse.backend.contracts.user;

/**
 * @author Equipe Compilador 
 * @version 1.0
 * Record para representar a resposta de atualização de um usuário.
 *
 * @param name     O nome do usuário.
 * @param email    O e-mail do usuário.
 * @param username O nome de usuário.
 * @param isActive O status ativo do usuário.
 */
public record UpdateResponse(
    String name,
    String email,
    String username,
    boolean isActive
){}
