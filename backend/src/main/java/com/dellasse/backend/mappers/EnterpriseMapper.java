package com.dellasse.backend.mappers;

import com.dellasse.backend.contracts.enterprise.EnterpriseCreateRequest;
import com.dellasse.backend.contracts.enterprise.EnterpriseUpdateRequest;
import com.dellasse.backend.models.Enterprise;

public class EnterpriseMapper {
    

    public static Enterprise toEntity(EnterpriseCreateRequest dtoRequest) {
        return new Enterprise(
            dtoRequest.name(),
            dtoRequest.address(),
            dtoRequest.phoneNumber(),
            dtoRequest.email(),
            dtoRequest.urlImage()
        );
    }

    public static Enterprise toEntityUpdate(EnterpriseUpdateRequest dtoRequest){
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
