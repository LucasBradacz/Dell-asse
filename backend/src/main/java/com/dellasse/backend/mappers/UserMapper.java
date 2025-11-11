package com.dellasse.backend.mappers;

import com.dellasse.backend.contracts.user.CreateRequest;
import com.dellasse.backend.models.User;

public class UserMapper {
    

    public static User toEntity(CreateRequest dtoRequest) {
        return new User(
            dtoRequest.name(),
            dtoRequest.email(),
            dtoRequest.username(),
            dtoRequest.password()
        );
    }
}
