package com.dellasse.backend.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.dellasse.backend.contracts.party.PartyCreateRequest;
import com.dellasse.backend.contracts.party.PartyResponse;
import com.dellasse.backend.models.Gallery;
import com.dellasse.backend.models.Party;

public class PartyMapper {
    
    public static Party toEntity(PartyCreateRequest create){
        Gallery gallery = null;
        if (create.galleryId() != null) {
            gallery = new Gallery(create.galleryId());
        }
        
        return new Party(
            create.title(),
            create.description(),
            create.products(),
            create.generateBudget(),
            create.observations(),
            create.imageURL(),
            gallery
        );
    }

    public static PartyResponse toResponse(Party entity) {
        return new PartyResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getObservations(),
                entity.getGenerateBudget(), // Passa Double direto (não converte para String)
                entity.getImgExample(),
                entity.getStatus(),
                // Novos campos que adicionamos no DTO
                entity.getUser() != null ? entity.getUser().getName() : "Usuário Desconhecido",
                entity.getLastAtualization(),
                entity.getProducts().stream()
                    .map(ProductMapper::toResponse)
                    .collect(Collectors.toList())
        );
    }
    
    public static List<PartyResponse> toResponse(List<Party> entity){
        return entity.stream()
                .map(PartyMapper::toResponse)
                .collect(Collectors.toList());
    }

    public static void updateEntity(Party entity, PartyCreateRequest update){
        entity.setTitle(update.title());
        entity.setDescription(update.description());
        entity.setGenerateBudget(update.generateBudget());
        entity.setObservations(update.observations());
        entity.setImgExample(update.imageURL());
    }
}