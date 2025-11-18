package com.dellasse.backend.util;

/**
 * @author Equipe Compilador 
 * @version 1.0
 * Enumeração para representar os status possíveis.
 * - PENDING: Status pendente.
 * - APPROVED: Status aprovado.
 * - REJECTED: Status rejeitado.
 * Cada status possui um valor associado em formato String.
 * - Método getValue() para obter o valor associado ao status.
 */
public enum StatusUtils {

    PENDING("PENDING"),
    APPROVED("APPROVED"),
    REJECTED("REJECTED");

    private final String value;

    StatusUtils(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}