package com.dellasse.backend.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dellasse.backend.contracts.party.PartyCreateRequest;
import com.dellasse.backend.contracts.party.PartyResponse;
import com.dellasse.backend.exceptions.DomainError;
import com.dellasse.backend.exceptions.DomainException;
import com.dellasse.backend.mappers.PartyMapper;
import com.dellasse.backend.models.Enterprise;
import com.dellasse.backend.models.Party;
import com.dellasse.backend.models.Role;
import com.dellasse.backend.models.User;
import com.dellasse.backend.repositories.PartyRepository;
import com.dellasse.backend.util.ConvertString;
import com.dellasse.backend.util.DateUtils;
import com.dellasse.backend.util.StatusUtils;

import jakarta.persistence.EntityManager;

@Service
public class PartyService {

    @Autowired
    private PartyRepository partyRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EntityManager entityManager;

    public Party create(PartyCreateRequest request, String token){
        UUID enterpriseId = null;
        UUID userId = ConvertString.toUUID(token);

        Party party = PartyMapper.toEntity(request);
        if (party == null) {
            throw new DomainException(DomainError.PARTY_INVALID);
        }

        List<Role> roles = userService.getRoles(userId);
        boolean isStaff = userService.isStaff(roles);
        if (isStaff) {
            enterpriseId = userService.validateUserEnterprise(userId);
        }
        applyDefaultValues(party, userId, enterpriseId);

        return partyRepository.save(party);
    }

    public List<PartyResponse> getAll(String token){
        UUID userId = ConvertString.toUUID(token);
        UUID enterpriseId = userService.validateUserEnterprise(userId);
        
        if (enterpriseId == null) {
            return PartyMapper.toResponse(partyRepository.findAllByUser_Uuid(userId));
        }
        List<Role> roles = userService.getRoles(userId);
        boolean isStaff = roles.stream()
                .anyMatch(role -> role.getName().equals(Role.Values.FUNCIONARIO.getName()) || role.getName().equals(Role.Values.ADMIN.getName()));

        if (!isStaff) {
           throw new DomainException(DomainError.USER_NOT_AUTHENTICATED); 
        }
        return PartyMapper.toResponse(partyRepository.findAllByEnterprise_Id(enterpriseId));
    }

    private void applyDefaultValues(Party party, UUID userId, UUID enterpriseId){
        party.setUser(entityManager.find(User.class, userId));
        if (enterpriseId != null) {
            party.setEnterprise(entityManager.find(Enterprise.class, enterpriseId));
        }
        party.setLastAtualization(DateUtils.now());
        party.setStatus(StatusUtils.PENDING.getValue());
    }
    
}
