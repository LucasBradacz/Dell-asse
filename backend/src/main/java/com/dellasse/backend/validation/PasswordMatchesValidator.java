package com.dellasse.backend.validation;

import com.dellasse.backend.contracts.user.CreateRequest;
import com.dellasse.backend.contracts.user.UpdateRequest;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

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
