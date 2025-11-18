package com.dellasse.backend.contracts.user;

import jakarta.validation.constraints.NotBlank;

/**
 * @author Equipe Compilador 
 * @version 1.0
 * Record para representar a requisição de login de um usuário.
 *
 * @param username O nome de usuário. Não pode estar em branco.
 * @param password A senha do usuário. Não pode estar em branco.
 */
public record LoginRequest(@NotBlank(message = "Username is required") String username, 
                           @NotBlank(message = "Password is required") String password) {
    
}
