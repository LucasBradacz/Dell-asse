package com.dellasse.backend.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dellasse.backend.contracts.enterprise.UpdateRequest;
import com.dellasse.backend.contracts.product.CreateRequest;
import com.dellasse.backend.contracts.product.UpdateResponse;
import com.dellasse.backend.mappers.ProductMapper;
import com.dellasse.backend.models.Enterprise;
import com.dellasse.backend.models.Product;
import com.dellasse.backend.models.User;
import com.dellasse.backend.repositories.ProductRepository;
import com.dellasse.backend.repositories.UserRepository;
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
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

 @Autowired
private ProductMapper productMapper; 

    public ResponseEntity<?> create(CreateRequest createRequest, String token){
        UUID userId = ConvertString.toUUID(token);

        var product = productMapper.toEntity(createRequest);

        UUID enterpriseId = validateUserEnterprise(userId);

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

    private UUID validateUserEnterprise(UUID userId){

        if (!userRepository.existsById(userId)){
            throw new DomainException(DomainError.USER_NOT_FOUND);
        }

        UUID enterpriseId = userRepository.findEnterprise_IdByUuid(userId);

        if (enterpriseId == null) {
            throw new DomainException(DomainError.USER_NOT_FOUND_ENTERPRISE);
        }

        if (!userRepository.existsByUuidAndEnterprise_Id(userId, enterpriseId)){
            throw new DomainException(DomainError.ENTERPRISE_FORBIDDEN);
        }
        return enterpriseId;
    }

    public UpdateResponse update(UpdateRequest request, Long productId, String token){
        UUID userId = ConvertString.toUUID(token);

        UUID enterpriseId = validateUserEnterprise(userId);

        validateProductUpdate(productMapper.toEntity(request));

        if (!productRepository.existsById(productId)){
            throw new DomainException(DomainError.PRODUCT_NOT_FOUND);
        }

        if(!productRepository.existsByIdAndEnterprise_Id(productId, enterpriseId)){
            throw new DomainException(DomainError.PRODUCT_NOT_FOUND);
        }

        
        return null;
    }

    private void validateProductUpdate(Product product){
        validateProduct(product);
    }
    private void validateProduct(Product product){
        if (product.getName() == null || product.getName().isEmpty()){
            throw new DomainException(DomainError.PRODUCT_INVALID_NAME);
        }
        if (product.getPrice() == null || product.getPrice() <= 0){
            throw new DomainException(DomainError.PRODUCT_INVALID_PRICE);
        }
        if (product.getStockQuantity() == null || product.getStockQuantity() <= 0){
            throw new DomainException(DomainError.PRODUCT_INVALID_QUANTITY);
        }
    }



}
