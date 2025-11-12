package com.dellasse.backend.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.dellasse.backend.contracts.enterprise.CreateEnterprise;
import com.dellasse.backend.exceptions.UserExeception;
import com.dellasse.backend.mappers.EnterpriseMapper;
import com.dellasse.backend.models.Enterprise;
import com.dellasse.backend.models.Role;
import com.dellasse.backend.repositories.EnterpriseRepository;
import com.dellasse.backend.repositories.RoleRepository;
import com.dellasse.backend.repositories.UserRepository;
import com.dellasse.backend.util.ConvertString;
public class EnterpriseService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EnterpriseRepository enterpriseRepository;

    public ResponseEntity<?> create(CreateEnterprise request, String id){
        UUID userUUID = ConvertString.toUUID(id);

        if (!userRepository.existsById(userUUID)){
            throw new UserExeception("User not found");
        }

        boolean temRole = userRepository.existsByUuidAndRoles_Id(userUUID, Role.Values.ADMIN.getRoleId());
        if (!temRole){
            throw new UserExeception("User not admin");
        }
        if (enterpriseRepository.existsByNameOrDocument(request.name(), request.document())) {
            throw new UserExeception("Enterprise already exists");
        }


        Enterprise enterprise = EnterpriseMapper.toEntity(request);
        enterpriseRepository.save(enterprise);

        return ResponseEntity.ok().build();
    }
 
}
