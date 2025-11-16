package com.dellasse.backend.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import com.dellasse.backend.contracts.party.PartyCreateRequest;
import com.dellasse.backend.contracts.party.PartyUpdateRequest;

import com.dellasse.backend.models.Party;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PartyMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lastAtualization", ignore = true)
    @Mapping(target = "status", ignore = true)
    Party toEntity(PartyCreateRequest createRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lastAtualization", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "productList", ignore = true)
    @Mapping(target = "user", ignore = true)
    Party toEntity(PartyUpdateRequest updateRequest);
}

