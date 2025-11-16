package com.dellasse.backend.mappers;


import com.dellasse.backend.contracts.party.PartyCreateRequest;
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
}

