package com.dellasse.backend.contracts.enterprise;

import jakarta.validation.constraints.NotBlank;

public record CreateEnterprise(
    
    @NotBlank(message = "Name is required") 
    String name, 
    
    @NotBlank(message = "Address is required") 
    String address, 

    @NotBlank(message = "Phone number is required")
    String phoneNumber, 
    
    @NotBlank(message = "Email is required")
    String email, 

    String urlImage
) {}
