package com.dellasse.backend.validation;

import com.dellasse.backend.contracts.user.UserCreateRequest;
import com.dellasse.backend.contracts.user.UserUpdateRequest;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object>{
    
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
     
        if (value instanceof UserCreateRequest req) {
            return req.password().equals(req.confirmPassword());
        }
     
        if (value instanceof UserUpdateRequest request){
            if (request.password() == null || request.confirmPassword() == null) {
                return true;
            }

            return request.password() != null && request.confirmPassword() != null && request.password().equals(request.confirmPassword());
        }

        return false;
    }
}
