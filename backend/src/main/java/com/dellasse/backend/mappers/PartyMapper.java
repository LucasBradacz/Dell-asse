package com.dellasse.backend.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.dellasse.backend.contracts.party.PartyCreateRequest;
import com.dellasse.backend.models.Party;
import com.dellasse.backend.models.Product;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PartyMapper {
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
}

