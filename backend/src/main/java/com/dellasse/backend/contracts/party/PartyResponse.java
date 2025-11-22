package com.dellasse.backend.contracts.party;

import java.util.List;

import com.dellasse.backend.contracts.product.ProductResponse;

/**
 * Representa o objeto de transferência de dados (DTO) para a resposta de uma party.
 * <p>
 * Este record encapsula os dados necessários que são enviados pela API
 * para recuperar os dados de uma party.
 *
 * @param id          O id da party.
 * @param title       O título da party.
 * @param observation Uma observação opcional sobre a party.
 * @param budget      O orçamento associado à party.
 * @param imageUrl    A URL da imagem associada à party.
 * @param status      O status atual da party.
 * @param products    Lista de produtos associados à party.
 */
public record PartyResponse(

    Long id,
    String title,
    String observation,
    String budget,
    String imageUrl,
    String status,
    List<ProductResponse> products

) {}
