package com.dellasse.backend.contracts.product;

public record ProductResponse(
    
    Long id,
    String name, 
    String description,
    Double price,
    Integer stockQuantity,
    String category,
    String imageUrl

) {}
