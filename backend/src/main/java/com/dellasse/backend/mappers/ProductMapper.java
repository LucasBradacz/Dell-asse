package com.dellasse.backend.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dellasse.backend.contracts.product.CreateRequest;
import com.dellasse.backend.contracts.product.UpdateRequest;
import com.dellasse.backend.models.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    default Product toEntity(Object dto) {
        if (dto instanceof CreateRequest create) {
            return toEntity(create);
        } else if (dto instanceof UpdateRequest update) {
            return toEntity(update);
        }
        throw new IllegalArgumentException("DTO type not supported");
    }


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "enterprise", ignore = true)
    @Mapping(target = "dateCreate", ignore = true)
    @Mapping(target = "dateUpdate", ignore = true)
    Product toEntity(CreateRequest createRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "enterprise", ignore = true)
    @Mapping(target = "dateCreate", ignore = true)
    @Mapping(target = "dateUpdate", ignore = true)
    Product toEntity(UpdateRequest updateRequest);
}