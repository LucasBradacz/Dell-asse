package com.dellasse.backend.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dellasse.backend.contracts.enterprise.EnterpriseCreateRequest;
import com.dellasse.backend.contracts.enterprise.EnterpriseResponse;
import com.dellasse.backend.contracts.enterprise.EnterpriseUpdateRequest;
import com.dellasse.backend.exceptions.DomainError;
import com.dellasse.backend.exceptions.DomainException;
import com.dellasse.backend.mappers.EnterpriseMapper;
import com.dellasse.backend.models.Enterprise;
import com.dellasse.backend.models.Role;
import com.dellasse.backend.repositories.EnterpriseRepository;
import com.dellasse.backend.repositories.UserRepository;
import com.dellasse.backend.util.ConvertString;

@Service
public class EnterpriseService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EnterpriseRepository enterpriseRepository;

    @Autowired
    private UserService userService;

    public ResponseEntity<?> create(EnterpriseCreateRequest request, String id){
        UUID userId = ConvertString.toUUID(id);

        if (!userRepository.existsById(userId)){
            throw new DomainException(DomainError.USER_NOT_FOUND);
        }

        boolean temRole = userRepository.existsByUuidAndRoles_Id(userId, Role.Values.ADMIN.getRoleId());
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

    public ResponseEntity<?> update(EnterpriseUpdateRequest request, UUID enterpriseId, String id){
        UUID userId = ConvertString.toUUID(id);

        UUID enterpriseUuid = userService.validateUserEnterprise(userId);

        if (enterpriseUuid != null){
            throw new DomainException(DomainError.ENTERPRISE_NOT_FOUND);
        }

        Enterprise enterprise = EnterpriseMapper.toEntityUpdate(request);
        
        if (enterprise != null){
            throw new DomainException(DomainError.ENTERPRISE_NOT_FOUND);
        }

        enterpriseRepository.save(enterprise);

        return ResponseEntity.ok().build();
    }

    public EnterpriseResponse findById(UUID enterpriseId, String token){
        UUID userId = ConvertString.toUUID(token);
        UUID enterpriseUuid = userService.validateUserEnterprise(userId);

        if (enterpriseUuid == null){
            throw new DomainException(DomainError.ENTERPRISE_NOT_FOUND);
        }

        Enterprise enterprise = enterpriseRepository.findById(enterpriseId)
                .orElseThrow(() -> new DomainException(DomainError.ENTERPRISE_NOT_FOUND));
        
        return EnterpriseMapper.toEnterpriseResponse(enterprise);
    }

    public List<EnterpriseResponse> findAll(String token){
        UUID userId = ConvertString.toUUID(token);
        UUID enterpriseUuid = userService.validateUserEnterprise(userId);

        if (enterpriseUuid == null){
            throw new DomainException(DomainError.ENTERPRISE_NOT_FOUND);
        }

        List<Enterprise> enterprises = enterpriseRepository.findAll();

        return EnterpriseMapper.toListEnterpriseResponse(enterprises);
    }
 
}
