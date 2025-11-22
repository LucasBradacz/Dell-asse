package com.dellasse.backend.mappers;

import com.dellasse.backend.contracts.user.UserCreateRequest;
import com.dellasse.backend.contracts.user.UserUpdateResponse;
import com.dellasse.backend.models.User;

public class UserMapper {
    

    public static User toEntity(UserCreateRequest dtoRequest) {
        return new User(
            dtoRequest.name(),
            dtoRequest.email(),
            dtoRequest.username(),
            dtoRequest.password(),
            dtoRequest.phone(),
            dtoRequest.birthday()
    
        );
    }


    public static UserUpdateResponse toResponse(User user) {
        return new UserUpdateResponse(
                user.getName(),
                user.getEmail(),
                user.getUsername(),
                user.isActive()
            );
    }
}
