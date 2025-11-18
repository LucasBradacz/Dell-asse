package com.dellasse.backend.contracts.enterprise;

import jakarta.validation.constraints.NotBlank;

 /**
  * @author Equipe Compilador 
  * @version 1.0
  * Record para representar a requisição de criação de uma nova empresa.
  *
  * @param name        O nome da empresa. Não pode estar em branco.
  * @param document    O documento da empresa. Não pode estar em branco.
  * @param address     O endereço da empresa. Não pode estar em branco.
  * @param phoneNumber O número de telefone da empresa. Não pode estar em branco.
  * @param email       O e-mail da empresa. Não pode estar em branco.
  * @param urlImage    A URL da imagem da empresa. Pode ser nulo.
  */
public record CreateRequest(
    
    @NotBlank(message = "Name is required") 
    String name, 
    
    @NotBlank(message = "Document is required")
    String document,

    @NotBlank(message = "Address is required") 
    String address, 

    @NotBlank(message = "Phone number is required")
    String phoneNumber, 
    
    @NotBlank(message = "Email is required")
    String email, 

    String urlImage
) {}
