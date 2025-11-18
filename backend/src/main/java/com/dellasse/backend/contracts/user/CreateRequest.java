package com.dellasse.backend.contracts.user;

import com.dellasse.backend.validation.PasswordMatches;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * @author Equipe Compilador 
 * @version 1.0
 * Record para representar a requisição de criação de um novo usuário.
 *
 * @param name            O nome do usuário. Não pode estar em branco.
 * @param email           O e-mail do usuário. Não pode estar em branco e deve ser um formato válido.
 * @param username        O nome de usuário. Não pode estar em branco e deve ter entre 4 e 20 caracteres.
 * @param password        A senha do usuário. Não pode estar em branco e deve ter pelo menos 6 caracteres.
 * @param confirmPassword A confirmação da senha do usuário. Deve corresponder à senha.
 */
@PasswordMatches
public record CreateRequest(

    @NotBlank(message = "Name is required") 
    String name, 
    
    @NotBlank(message = "Email is required")  
    @Email(message = "Invalid email format") 
    String email, 

    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    String username,

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    String password,

    @NotBlank(message = "Confirm Password is required")
    @Size(min = 6, message = "Confirm Password must be at least 6 characters long")
    String confirmPassword
    
) {}
