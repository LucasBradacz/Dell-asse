package com.dellasse.backend.contracts.product;

public record ProductUpdateResponse(
    
    Long id,
    String name,
    String description,
    Double price,
    Integer stockQuantity,
    String imageUrl

) {}
