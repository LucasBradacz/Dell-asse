package com.dellasse.backend.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.dellasse.backend.contracts.enterprise.EnterpriseCreateRequest;
import com.dellasse.backend.contracts.enterprise.EnterpriseResponse;
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

    public static EnterpriseResponse toEnterpriseResponse(Enterprise enterprise){
        return new EnterpriseResponse(
            enterprise.getId(),
            enterprise.getName(),
            enterprise.getDocument(),
            enterprise.getAddress(),
            enterprise.getPhoneNumber(),
            enterprise.getEmail(),
            enterprise.getUrlImage()
        );
    }

    public static List<EnterpriseResponse> toListEnterpriseResponse(List<Enterprise> enterprises){
        return enterprises.stream()
                .map(EnterpriseMapper::toEnterpriseResponse)
                .collect(Collectors.toList());
    }
}
