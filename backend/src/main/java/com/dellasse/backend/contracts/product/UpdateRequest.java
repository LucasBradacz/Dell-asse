package com.dellasse.backend.contracts.product;

public record UpdateRequest(
    String name,
    String description,
    Double price,
    Integer stockQuantity,
    String imageUrl,
    String category
) {}
