package com.dellasse.backend.contracts.user;

import com.dellasse.backend.validation.PasswordMatches;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@PasswordMatches
public record UserCreateRequest(
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
