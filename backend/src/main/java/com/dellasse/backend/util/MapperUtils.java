package com.dellasse.backend.util;

import com.dellasse.backend.contracts.product.CreateRequest;
import com.dellasse.backend.contracts.product.UpdateRequest;
import com.dellasse.backend.mappers.ProductMapper;
import com.dellasse.backend.models.Product;

public class MapperUtils {

    private MapperUtils() {}

    public static Product toEntity(Object dto, ProductMapper mapper) {
        if (dto instanceof CreateRequest create) {
            return mapper.toEntity(create);
        } else if (dto instanceof UpdateRequest update) {
            return mapper.toEntity(update);
        }
        throw new IllegalArgumentException("DTO type not supported");
    }
}