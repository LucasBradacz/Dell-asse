package com.dellasse.backend.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dellasse.backend.contracts.product.ProductCreateRequest;
import com.dellasse.backend.contracts.product.ProductUpdateRequest;
import com.dellasse.backend.contracts.product.UpdateResponse;
import com.dellasse.backend.mappers.ProductMapper;
import com.dellasse.backend.models.Enterprise;
import com.dellasse.backend.models.Product;
import com.dellasse.backend.models.User;
import com.dellasse.backend.repositories.ProductRepository;
import com.dellasse.backend.util.ConvertString;
import com.dellasse.backend.util.DateUtils;

import jakarta.persistence.EntityManager;

import com.dellasse.backend.exceptions.DomainError;
import com.dellasse.backend.exceptions.DomainException;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EntityManager entityManager;

    public ResponseEntity<?> create(ProductCreateRequest createRequest, String token){
        UUID userId = ConvertString.toUUID(token);

        Product product = ProductMapper.toEntityCreateProduct(createRequest);
        if (product == null){
            throw new NullPointerException();
        }

        UUID enterpriseId = userService.validateUserEnterprise(userId);

        setValueDefault(product, userId, enterpriseId);
        productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private void setValueDefault(Product product, UUID userId, UUID enterpriseId){
        product.setUser(entityManager.getReference(User.class, userId));
        product.setEnterprise(entityManager.getReference(Enterprise.class, enterpriseId));
        product.setDateCreate(DateUtils.now());
        product.setDateUpdate(DateUtils.now());
    }

    public UpdateResponse update(ProductUpdateRequest request, Long productId, String token) {
        UUID userId = ConvertString.toUUID(token);
        UUID enterpriseId = userService.validateUserEnterprise(userId);

        Product entity = productRepository.findById(productId)
                .orElseThrow(() -> new DomainException(DomainError.PRODUCT_NOT_FOUND));

        existsProductEnterprise(productId, enterpriseId);
        setValueUpdate(entity, request);

        entity.setDateUpdate(DateUtils.now());

        productRepository.save(entity);

        return ProductMapper.toContractUpdateResponse(entity);
    }

    private void existsProductEnterprise(Long productId, UUID enterpriseId){
        if (enterpriseId == null || productId == null){
            throw new DomainException(DomainError.PRODUCT_OR_ENTERPRISE_NOT_FOUND_INTERNAL);
        }

        if (!productRepository.existsById(productId)){
            throw new DomainException(DomainError.PRODUCT_NOT_FOUND);
        }

        if(!productRepository.existsByIdAndEnterprise_Id(productId, enterpriseId)){
            throw new DomainException(DomainError.PRODUCT_NOT_FOUND);
        }
    }

    private void setValueUpdate(Product entity, ProductUpdateRequest request){
        if (request.name() != null){
            entity.setName(request.name());
        }
        if (request.description() != null){
            entity.setDescription(request.description());
        }
        if (request.price() != null){
            entity.setPrice(request.price());
        }
        if (request.stockQuantity() != null){
            entity.setStockQuantity(request.stockQuantity());
        }
        if (request.category() != null){
            entity.setCategory(request.category());
        }
        if (request.imageUrl() != null){
            entity.setImageUrl(request.imageUrl());
        }
    }
}
