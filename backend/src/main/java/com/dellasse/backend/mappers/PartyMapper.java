package com.dellasse.backend.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.dellasse.backend.contracts.party.PartyCreateRequest;
import com.dellasse.backend.models.Party;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PartyMapper {
<<<<<<< Updated upstream
    public static Party toEntity(PartyCreateRequest create) {
        return new Party(
            create.title(),
            create.description(),
            create.productList().stream().map(productId -> new Product(productId)).toList(),
            create.generateBudget(),
            create.observations(),
            create.imageURL()
        );
    }
=======
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lastAtualization", ignore = true)
    @Mapping(target = "status", ignore = true)
    Party toEntity(CreateRequest createRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lastAtualization", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "productList", ignore = true)
    @Mapping(target = "user", ignore = true)
    Party toEntity(UpdateRequest updateRequest);
>>>>>>> Stashed changes
}

