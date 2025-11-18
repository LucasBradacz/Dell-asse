package com.dellasse.backend.validation;

import com.dellasse.backend.contracts.user.CreateRequest;
import com.dellasse.backend.contracts.user.UpdateRequest;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author Equipe Compilador 
 * @version 1.0
 * Validador para a anotação PasswordMatches, que verifica se as senhas coincidem.
 * Implementa a lógica de validação para as classes que possuem campos de senha e confirmação de senha.
 * Suporta tanto CreateRequest quanto UpdateRequest.
 * - Retorna true se as senhas coincidirem ou se os campos de senha forem nulos no caso de UpdateRequest.
 * - Retorna false caso contrário.
 */
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object>{
    
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
     
        if (value instanceof CreateRequest req) {
            return req.password().equals(req.confirmPassword());
        }
     
        if (value instanceof UpdateRequest request){
            if (request.password() == null || request.confirmPassword() == null) {
                return true;
            }

            return request.password() != null && request.confirmPassword() != null && request.password().equals(request.confirmPassword());
        }

        return false;
    }
}
