package com.dellasse.backend.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.dellasse.backend.contracts.enterprise.CreateRequest;
import com.dellasse.backend.contracts.enterprise.UpdateRequest;
import com.dellasse.backend.exceptions.DomainError;
import com.dellasse.backend.exceptions.DomainException;
import com.dellasse.backend.mappers.EnterpriseMapper;
import com.dellasse.backend.models.Enterprise;
import com.dellasse.backend.models.Role;
import com.dellasse.backend.repositories.EnterpriseRepository;
import com.dellasse.backend.repositories.UserRepository;
import com.dellasse.backend.util.ConvertString;
public class EnterpriseService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EnterpriseRepository enterpriseRepository;

    public ResponseEntity<?> create(CreateRequest request, String id){
        UUID userUUID = ConvertString.toUUID(id);

        if (!userRepository.existsById(userUUID)){
            throw new DomainException(DomainError.USER_NOT_FOUND);
        }

        boolean temRole = userRepository.existsByUuidAndRoles_Id(userUUID, Role.Values.ADMIN.getRoleId());
        if (!temRole){
            throw new DomainException(DomainError.USER_NOT_ADMIN);
        }
        if (enterpriseRepository.existsByNameOrDocument(request.name(), request.document())) {
            throw new DomainException(DomainError.ENTERPRISE_EXISTS);
        }


        Enterprise enterprise = EnterpriseMapper.toEntity(request);
        enterpriseRepository.save(enterprise);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> update(UpdateRequest request, UUID enterpriseId, String id){
        UUID user = ConvertString.toUUID(id);

        if (!enterpriseRepository.existsById(enterpriseId)){
            throw new DomainException(DomainError.ENTERPRISE_NOT_FOUND);
        }

        if (!userRepository.existsByUuidAndEnterprise_Id(user, enterpriseId)){
            throw new DomainException(DomainError.USER_NOT_LINKED);
        }

        Enterprise enterprise = enterpriseRepository.findById(enterpriseId).get();
        // EnterpriseMapper.toUpdateEntity(request, enterprise);
        enterpriseRepository.save(enterprise);

        return ResponseEntity.ok().build();
    }
 
}
