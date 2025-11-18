package com.dellasse.backend.mappers;

import com.dellasse.backend.contracts.enterprise.CreateRequest;
import com.dellasse.backend.contracts.enterprise.UpdateRequest;
import com.dellasse.backend.models.Enterprise;

/**
 * @author Equipe Compilador 
 * @version 1.0
 * Classe para mapear entre DTOs de requisição e a entidade Enterprise.
 */
public class EnterpriseMapper {

    public static Enterprise toEntity(CreateRequest dtoRequest) {
        return new Enterprise(
            dtoRequest.name(),
            dtoRequest.address(),
            dtoRequest.phoneNumber(),
            dtoRequest.email(),
            dtoRequest.urlImage()
        );
    }

    public static Enterprise toEntityUpdate(UpdateRequest dtoRequest){
        return new Enterprise(
            dtoRequest.name(),
            dtoRequest.document(),
            dtoRequest.address(),
            dtoRequest.phoneNumber(),
            dtoRequest.email(),
            dtoRequest.urlImage()
        );
    }
}
