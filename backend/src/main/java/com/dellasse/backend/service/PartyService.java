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


    /// quando eu tiver um token que o usuario tiver vinculado a empresa ele vai puxar do byEnterprise.
    /// se meu usuario nao tiver um empresa vinculada a ele vai puxar somente a galeria dele
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

    public PartyResponse getById(Long id, String token){
        UUID userId = ConvertString.toUUID(token);
        Party party = partyRepository.findById(id)
                .orElseThrow(() -> new DomainException(DomainError.PARTY_NOT_FOUND));
        if (!party.getUser().getUuid().equals(userId)) {
            throw new DomainException(DomainError.USER_NOT_AUTHENTICATED);
        }
        return PartyMapper.toResponse(party);
    }

    public PartyResponse update(Long id, PartyCreateRequest request, String token){
        UUID userId = ConvertString.toUUID(token);
        Party party = partyRepository.findById(id)
                .orElseThrow(() -> new DomainException(DomainError.PARTY_NOT_FOUND));
        if (!party.getUser().getUuid().equals(userId)) {
            throw new DomainException(DomainError.USER_NOT_AUTHENTICATED);
        }
        PartyMapper.updateEntity(party, request);
        applyDefaultValues(party, userId, party.getEnterprise().getId());
        return PartyMapper.toResponse(partyRepository.save(party));
    }
}
