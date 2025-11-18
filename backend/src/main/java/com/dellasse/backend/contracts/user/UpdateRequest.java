package com.dellasse.backend.contracts.user;

/**
 * @author Equipe Compilador 
 * @version 1.0
 * Record para representar a requisição de atualização de um usuário.
 *
 * @param name            O nome do usuário. Pode ser nulo.
 * @param email           O e-mail do usuário. Pode ser nulo.
 * @param username        O nome de usuário. Pode ser nulo.
 * @param password        A senha do usuário. Pode ser nulo.
 * @param confirmPassword A confirmação da senha do usuário. Pode ser nulo.
 * @param active          O status ativo do usuário.
 */
public record UpdateRequest(
    String name,
    String email,
    String username,
    String password,
    String confirmPassword,
    boolean active
) {}
