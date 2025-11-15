package com.dellasse.backend.contracts.product;

public record UpdateResponse(
    String name,
    String description,
    String price,
    String stockQuantity,
    String imageUrl,
    String category
) {} 
