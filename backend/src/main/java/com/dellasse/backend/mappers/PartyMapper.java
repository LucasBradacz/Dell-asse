package com.dellasse.backend.mappers;


import java.util.List;
import java.util.stream.Collectors;

import com.dellasse.backend.contracts.party.PartyCreateRequest;
import com.dellasse.backend.contracts.party.PartyResponse;
import com.dellasse.backend.models.Party;

public class PartyMapper {
    public static Party toEntity(PartyCreateRequest create){
        return new Party(
            create.title(),
            create.description(),
            create.products(),
            create.generateBudget(),
            create.observations(),
            create.imageURL()
        );
    }
    public static PartyResponse toResponse(Party entity) {
        return new PartyResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getObservations(),
                String.valueOf(entity.getGenerateBudget()),
                entity.getImgExample(),
                entity.getStatus(),
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

