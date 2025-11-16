package com.dellasse.backend.mappers;

import com.dellasse.backend.contracts.product.CreateRequest;
import com.dellasse.backend.contracts.product.ProductUpdateRequest;
import com.dellasse.backend.models.Product;

public class ProductMapper {

    public static Product toEntityCreateProduct(CreateRequest createRequest) {
        return new Product(
            createRequest.name(), 
            createRequest.description(), 
            createRequest.price(), 
            createRequest.stockQuantity(), 
            createRequest.category(), 
            createRequest.imageUrl());
    }

    public static Product toEntityUpdateProduct(ProductUpdateRequest request) {
        return new Product(
            request.name(), 
            request.description(), 
            request.price(), 
            request.stockQuantity(), 
            request.category(), 
            request.imageUrl()
        );
    }
    
}