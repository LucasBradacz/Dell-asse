package com.dellasse.backend.contracts.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author Equipe Compilador 
 * @version 1.0
 * Record para representar a requisição de criação de um novo produto.
 *
 * @param name          O nome do produto. Não pode estar em branco.
 * @param description   A descrição do produto. Pode ser nulo.
 * @param price         O preço do produto. Não pode ser nulo e deve ser maior que zero.
 * @param stockQuantity A quantidade em estoque do produto. Não pode ser nulo e deve ser maior que zero.
 * @param category      A categoria do produto. Não pode estar em branco.
 * @param imageUrl      A URL da imagem do produto. Pode ser nulo.
 */
public record ProductCreateRequest(

    @NotBlank(message = "Name is required")
    String name,

    String description,

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    Double price,

    @NotNull(message = "Stock Quantity is required")
    @DecimalMin(value = "0", inclusive = false, message = "Stock Quantity must be greater than zero")
    Integer stockQuantity,

    @NotBlank(message = "Category is required")
    String category,
    
    String imageUrl
) {}
