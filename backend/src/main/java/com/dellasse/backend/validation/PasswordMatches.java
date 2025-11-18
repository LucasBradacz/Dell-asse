package com.dellasse.backend.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;

/**
 * @author Equipe Compilador 
 * @version 1.0
 * Anotação personalizada para validar se as senhas coincidem.
 * Utilizada em classes que possuem campos de senha e confirmação de senha.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class)
public @interface PasswordMatches {
    String message() default "Passwords do not match";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
}
