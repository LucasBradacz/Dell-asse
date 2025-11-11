package com.dellasse.backend.validation;

import com.dellasse.backend.contracts.user.CreateRequest;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, CreateRequest>{
    
    @Override
    public boolean isValid(CreateRequest request, ConstraintValidatorContext context) {
        return request.password().equals(request.confirmPassword());
    }
}
