package com.dellasse.backend.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.dellasse.backend.contracts.party.CreateRequest;
import com.dellasse.backend.contracts.party.UpdateRequest;
import com.dellasse.backend.models.Party;
import com.dellasse.backend.models.Product;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PartyMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lastAtualization", ignore = true)
    @Mapping(target = "status", ignore = true)
    Party toEntity(CreateRequest createRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lastAtualization", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "productList", ignore = true)
    @Mapping(target = "user", ignore = true)
    Product toEntity(UpdateRequest updateRequest);
}

