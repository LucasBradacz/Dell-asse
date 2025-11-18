package com.dellasse.backend.mappers;

import com.dellasse.backend.contracts.user.CreateRequest;
import com.dellasse.backend.contracts.user.UpdateResponse;
import com.dellasse.backend.models.User;

/**
 * @author Equipe Compilador 
 * @version 1.0
 * Classe para mapear entre DTOs de requisição/resposta e a entidade User.
 */
public class UserMapper {

    public static User toEntity(CreateRequest dtoRequest) {
        return new User(
            dtoRequest.name(),
            dtoRequest.email(),
            dtoRequest.username(),
            dtoRequest.password()
        );
    }


    public static UpdateResponse toResponse(User user) {
        return new UpdateResponse(
                user.getName(),
                user.getEmail(),
                user.getUsername(),
                user.isActive()
            );
    }
}
