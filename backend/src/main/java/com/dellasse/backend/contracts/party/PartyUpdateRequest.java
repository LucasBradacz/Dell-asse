package com.dellasse.backend.contracts.party;

/**
 * @author Equipe Compilador 
 * @version 1.0
 * Record para representar a requisição de atualização de uma festa.
 *
 * @param title           O título da festa. Pode ser nulo.
 * @param description     A descrição da festa. Pode ser nulo.
 * @param observations    Observações adicionais sobre a festa. Pode ser nulo.
 * @param lastAtualization A data da última atualização da festa. Pode ser nulo.
 * @param imageURL        A URL da imagem da festa. Pode ser nulo.
 * @param generateBudget  O orçamento gerado para a festa. Pode ser nulo.
 */
public record PartyUpdateRequest (
    String title,
    String description, 
    String observations, 
    String lastAtualization,  
    String imageURL, 
    Double generateBudget
) {}

