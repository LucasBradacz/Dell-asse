package com.dellasse.backend.contracts.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateRequest(

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
