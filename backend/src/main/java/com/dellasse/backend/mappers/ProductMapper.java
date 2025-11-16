package com.dellasse.backend.mappers;

import com.dellasse.backend.contracts.product.ProductCreateRequest;
import com.dellasse.backend.contracts.product.ProductUpdateRequest;
import com.dellasse.backend.contracts.product.UpdateResponse;
import com.dellasse.backend.models.Product;

public class ProductMapper {

    public static Product toEntityCreateProduct(ProductCreateRequest createRequest) {
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

    public static UpdateResponse toContractUpdateResponse(Product product) {
        return new UpdateResponse(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getStockQuantity(),
            product.getImageUrl()
        );
    }
    
}