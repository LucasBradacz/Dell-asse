package com.dellasse.backend.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dellasse.backend.contracts.product.CreateRequest;
import com.dellasse.backend.contracts.product.UpdateRequest;
import com.dellasse.backend.models.Product;

@Mapper
public interface ProductMapper {
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