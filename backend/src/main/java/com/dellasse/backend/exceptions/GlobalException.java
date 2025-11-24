package com.dellasse.backend.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Manipulador global de exceções para a aplicação.
 * <p>
 * Esta classe captura exceções lançadas pelos controladores REST e
 * retorna respostas HTTP apropriadas com mensagens de erro detalhadas.
 *
 * @author  Dell'Asse
 * @version 1.0
 * @since 2025-11-21
 */
@RestControllerAdvice
public class GlobalException {
    
    /** 
     * Manipula exceções de validação de argumentos de método.
     *
     * @param ex A exceção lançada durante a validação.
     * @return ResponseEntity contendo os detalhes dos erros de validação.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
       
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            if (error instanceof FieldError fieldError) {
                errors.put(fieldError.getField(), error.getDefaultMessage());
            } else {
                errors.put(error.getObjectName(), error.getDefaultMessage());
            }
        });

        return ResponseEntity.badRequest().body(errors);
    }

    /** 
     * Manipula exceções genéricas.
     *
     * @param ex A exceção genérica lançada.
     * @return ResponseEntity contendo uma mensagem de erro genérica.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Internal server error");

        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    /** 
     * Manipula exceções de domínio personalizadas.
     *
     * @param ex A exceção de domínio lançada.
     * @return ResponseEntity contendo a mensagem de erro e o status apropriado.
     */
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<?> handleUserException(DomainException ex) {
        return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
    }
}
