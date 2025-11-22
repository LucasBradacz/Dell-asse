package com.dellasse.backend.mappers;

import com.dellasse.backend.contracts.product.ProductCreateRequest;
import com.dellasse.backend.contracts.product.ProductResponse;
import com.dellasse.backend.contracts.product.ProductUpdateRequest;
import com.dellasse.backend.contracts.product.ProductUpdateResponse;
import com.dellasse.backend.models.Product;

public class ProductMapper {

    public static Product toEntityCreateProduct(ProductCreateRequest createRequest) {
        return new Product(
            createRequest.name(), 
            createRequest.description(), 
            createRequest.price(), 
            createRequest.stockQuantity(), 
            createRequest.category(), 
            createRequest.imageUrl()
        );
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

    public static ProductUpdateResponse toContractUpdateResponse(Product product) {
        return new ProductUpdateResponse(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getStockQuantity(),
            product.getImageUrl()
        );
    }

        public static ProductResponse toResponse(Product product) {
        return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getStockQuantity(),
            product.getCategory(),
            product.getImageUrl()
        );
    }
    
}