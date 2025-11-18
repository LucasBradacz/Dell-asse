package com.dellasse.backend.exceptions;

/**
 * @author Equipe Compilador 
 * @version 1.0
 * Exceção personalizada para representar erros de domínio na aplicação.
 * Contém um DomainError que fornece detalhes sobre o erro ocorrido.
 */
public class DomainException extends RuntimeException {

    private final DomainError error;

    public DomainException(DomainError error) {
        super(error.getMessage());
        this.error = error;
    }

    public int getStatus() {
        return error.getStatus();
    }
}
