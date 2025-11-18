package com.dellasse.backend.contracts.product;

/**
 * @author Equipe Compilador 
 * @version 1.0
 * Record para representar a resposta de atualização de um produto.
 *
 * @param id            O ID do produto.
 * @param name          O nome do produto.
 * @param description   A descrição do produto.
 * @param price         O preço do produto.
 * @param stockQuantity A quantidade em estoque do produto.
 * @param imageUrl      A URL da imagem do produto.
 */
public record UpdateResponse(
    Long id,
    String name,
    String description,
    Double price,
    Integer stockQuantity,
    String imageUrl
) {}
