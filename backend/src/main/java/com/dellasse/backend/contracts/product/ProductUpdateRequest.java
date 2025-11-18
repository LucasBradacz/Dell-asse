package com.dellasse.backend.contracts.product;

/**
 * @author Equipe Compilador 
 * @version 1.0
 * Record para representar a requisição de atualização de um produto.
 *
 * @param name          O nome do produto. Pode ser nulo.
 * @param description   A descrição do produto. Pode ser nulo.
 * @param price         O preço do produto. Pode ser nulo.
 * @param stockQuantity A quantidade em estoque do produto. Pode ser nulo.
 * @param imageUrl      A URL da imagem do produto. Pode ser nulo.
 * @param category      A categoria do produto. Pode ser nulo.
 */
public record ProductUpdateRequest(
    String name,
    String description,
    Double price,
    Integer stockQuantity,
    String imageUrl,
    String category
) {}