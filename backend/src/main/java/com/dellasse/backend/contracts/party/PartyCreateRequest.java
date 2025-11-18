package com.dellasse.backend.contracts.party;

import java.util.List;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author Equipe Compilador 
 * @version 1.0
 * Record para representar a requisição de criação de uma nova festa.
 *
 * @param title           O título da festa. Não pode estar em branco.
 * @param description     A descrição da festa. Não pode estar em branco.
 * @param products        A lista de IDs dos produtos associados à festa.
 * @param generateBudget  O orçamento gerado para a festa. Não pode ser nulo e deve ser maior ou igual a zero.
 * @param observations    Observações adicionais sobre a festa. Pode ser nulo.
 * @param imageURL        A URL da imagem da festa. Pode ser nulo.
 */
public record PartyCreateRequest(

    @NotBlank(message = "Title is required")
    String title,

    @NotBlank(message = "Description is required")
    String description,

    List<Long> products,

    @NotNull(message = "Generate Budget is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Generate Budget must be zero or greater")
    Double generateBudget,

    String observations,
    String imageURL

) {}


