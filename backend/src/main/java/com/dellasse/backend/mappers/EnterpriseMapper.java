package com.dellasse.backend.mappers;

import com.dellasse.backend.contracts.enterprise.CreateEnterprise;
import com.dellasse.backend.models.Enterprise;

public class EnterpriseMapper {
    

    public static Enterprise toEntity(CreateEnterprise dtoRequest) {
        return new Enterprise(
            dtoRequest.name(),
            dtoRequest.address(),
            dtoRequest.phoneNumber(),
            dtoRequest.email(),
            dtoRequest.urlImage()
        );
    }
}
