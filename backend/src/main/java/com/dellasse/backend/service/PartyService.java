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
import com.dellasse.backend.models.Gallery;
import com.dellasse.backend.models.Party;
import com.dellasse.backend.models.Role;
import com.dellasse.backend.models.User;
import com.dellasse.backend.repositories.PartyRepository;
import com.dellasse.backend.util.ConvertString;
import com.dellasse.backend.util.DateUtils;
import com.dellasse.backend.util.StatusUtils;

import jakarta.persistence.EntityManager;

/**
 * Serviço para a entidade Party.
 * <p>
 * Fornece métodos para operações relacionadas às festas.
 */
@Service
public class PartyService {

    @Autowired
    private PartyRepository partyRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EntityManager entityManager;

    /**
     * Cria uma nova festa.
     *
     * @param request Dados da festa a ser criada.
     * @param token   Token do usuário que está criando a festa.
     * @return A festa criada.
     */
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
        if (request.galleryId() != null) {
            Gallery gallery = entityManager.find(Gallery.class, request.galleryId());
            party.setGallery(gallery);
        } else {
            party.setGallery(null);
        }
        applyDefaultValues(party, userId, enterpriseId);
        
        return partyRepository.save(party);
    }


    /// quando eu tiver um token que o usuario tiver vinculado a empresa ele vai puxar do byEnterprise.
    /// se meu usuario nao tiver um empresa vinculada a ele vai puxar somente a galeria dele
    public List<PartyResponse> getAll(String token){
        UUID userId = ConvertString.toUUID(token);
        
        List<Role> roles = userService.getRoles(userId);
        boolean isAdmin = roles.stream()
                .anyMatch(role -> role.getName().equalsIgnoreCase("ADMIN") || role.getName().equalsIgnoreCase("ROLE_ADMIN"));

        if (isAdmin) {
             return PartyMapper.toResponse(partyRepository.findAll());
        }

        UUID enterpriseId = userService.validateUserEnterprise(userId);
        
        if (enterpriseId == null) {
            return PartyMapper.toResponse(partyRepository.findAllByUser_Uuid(userId));
        }

        boolean isStaff = roles.stream()
                .anyMatch(role -> role.getName().equals(Role.Values.FUNCIONARIO.getName()));

        if (!isStaff) {
           throw new DomainException(DomainError.USER_NOT_AUTHENTICATED); 
        }

        return PartyMapper.toResponse(partyRepository.findAllByEnterprise_Id(enterpriseId));
    }

    /**
     * Aplica valores padrão à festa.
     * @param party
     * @param userId
     * @param enterpriseId
     */
    private void applyDefaultValues(Party party, UUID userId, UUID enterpriseId){
        party.setUser(entityManager.find(User.class, userId));
        if (enterpriseId != null) {
            party.setEnterprise(entityManager.find(Enterprise.class, enterpriseId));
        }
        party.setLastAtualization(DateUtils.now());
        party.setStatus(StatusUtils.PENDING.getValue());
    }

    /**
     * Busca uma festa pelo ID.
     *
     * @param id    ID da festa a ser buscada.
     * @param token Token do usuário que está realizando a busca.
     * @return Dados da festa encontrada.
     */
    public PartyResponse getById(Long id, String token){
        UUID userId = ConvertString.toUUID(token);
        Party party = partyRepository.findById(id)
                .orElseThrow(() -> new DomainException(DomainError.PARTY_NOT_FOUND));
        if (!party.getUser().getUuid().equals(userId)) {
            throw new DomainException(DomainError.USER_NOT_AUTHENTICATED);
        }
        return PartyMapper.toResponse(party);
    }

    /**
     * Atualiza uma festa existente.
     *
     * @param id      ID da festa a ser atualizada.
     * @param request Dados atualizados da festa.
     * @param token   Token do usuário que está realizando a atualização.
     * @return Dados da festa atualizada.
     */
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

    /**
     * Atualiza o status de uma festa.
     *
     * @param id     ID da festa a ser atualizada.
     * @param status Novo status da festa.
     * @param token  Token do usuário que está realizando a atualização.
     */
    public void updateStatus(Long id, String status, String token) {
        UUID userId = ConvertString.toUUID(token);

        Party party = partyRepository.findById(id)
                .orElseThrow(() -> new DomainException(DomainError.PARTY_NOT_FOUND));
        
        String cleanStatus = status.replace("\"", "");
        
        party.setStatus(cleanStatus);
        partyRepository.save(party);
    }

    /**
     * Busca todas as festas públicas.
     *
     * @return Lista de festas públicas encontradas.
     */
    public List<PartyResponse> getPublicGallery() {
        List<Party> todasFestas = partyRepository.findAll();
        return PartyMapper.toResponse(todasFestas);
    }
}
