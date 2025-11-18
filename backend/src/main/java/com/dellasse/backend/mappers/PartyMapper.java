package com.dellasse.backend.mappers;

import com.dellasse.backend.contracts.party.PartyCreateRequest;
import com.dellasse.backend.models.Party;

/**
 * @author Equipe Compilador 
 * @version 1.0
 * Classe para mapear entre DTOs de requisição e a entidade Party.
 */
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

