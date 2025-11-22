package com.dellasse.backend.contracts.gallery;

import java.util.List;

import com.dellasse.backend.contracts.image.ImageCreateRequest;

import jakarta.validation.constraints.NotBlank;
/**
 * Representa o objeto de transferência de dados (DTO) para a resposta de uma Galeria.
 * <p>
 * Este record encapsula os dados necessários que são enviados pela API
 * para recuperar os dados de uma galeria.
 *
 * @param name        O nome oficial da galeria.
 * @param description Uma descrição opcional da galeria.
 * @param alt         Uma alternativa opcional para a galeria.
 * @param image       Uma lista de URLs de imagens que compõem a galeria.
 */
public record GalleryResponse(
    
    @NotBlank String name,
    @NotBlank String description,
    @NotBlank String alt,
    List<ImageCreateRequest> image) {
    
}
