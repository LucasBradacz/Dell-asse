package com.dellasse.backend.exceptions;

/**
 * @author Equipe Compilador 
 * @version 1.0
 * Enum para representar erros de domínio na aplicação.
 * Cada erro possui uma mensagem e um código de status HTTP associado.
 */
public enum DomainError {

    USER_NOT_FOUND("User not found", 404),
    USER_INVALID_PASSWORD("Invalid password", 401),
    USER_FORBIDDEN("The user does not have permission", 403),
    USER_NOT_LINKED("The user is not linked", 403),
    USER_ALREADY_EXISTS("User already exists", 409),
    USER_NOT_ADMIN("User is not admin", 403),
    USER_CANNOT_UPDATE_DATA("The user does not have permission to change the data.", 403),
    USER_NOT_FOUND_ENTERPRISE("User not found in enterprise", 404),
    USER_EMAIL_ALREADY_EXISTS("Email already exists", 409),
    USER_REQUIRE_USERNAME("Username is requerid", 400),
    USER_NOT_AUTHENTICATED("User not authenticated", 401),
    USER_NOT_FOUND_INTERNAL("User not found", 500),

    ENTERPRISE_NOT_FOUND("Enterprise not found", 404),
    ENTERPRISE_EXISTS("Enterprise already exists", 409),
    ENTERPRISE_FORBIDDEN("You do not have permission to access this enterprise", 403),
    ENTERPRISE_EXPIRED("Enterprise expired", 403),
    ENTERPRISE_NOT_FOUND_INTERNAL("Enterprise not found", 500),

    PRODUCT_NOT_FOUND("Product not found", 404),
    PRODUCT_ALREADY_EXISTS("Product already exists", 409),
    PRODUCT_INVALID_NAME("Product name is invalid", 400),
    PRODUCT_INVALID_PRICE("Product price is invalid", 400),
    PRODUCT_INVALID_QUANTITY("Product quantity is invalid", 400),
    PRODUCT_NOT_FOUND_INTERNAL("Product not found", 500),
    PRODUCT_OR_ENTERPRISE_NOT_FOUND_INTERNAL("Product or enterprise not found", 500);

    private final String message;
    private final int status;

    DomainError(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() { return message; }
    public int getStatus() { return status; }
}
