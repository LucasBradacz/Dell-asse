package com.dellasse.backend.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import com.dellasse.backend.contracts.party.PartyCreateRequest;
import com.dellasse.backend.contracts.party.PartyUpdateRequest;

import com.dellasse.backend.models.Party;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PartyMapper {
    public static Party toEntity(PartyCreateRequest createRequest){
        return new Party(
            createRequest.title(),
            createRequest.description(),
            createRequest.productList(),
            createRequest.generateBudget(),
            createRequest.observations(),
            createRequest.imageURL()
        );
    }
}

