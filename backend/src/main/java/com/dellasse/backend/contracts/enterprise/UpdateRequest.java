package com.dellasse.backend.contracts.enterprise;

/**
 * @author Equipe Compilador 
 * @version 1.0
 * Record para representar a requisição de atualização de uma empresa.
 *
 * @param name        O nome da empresa. Pode ser nulo.
 * @param document    O documento da empresa. Pode ser nulo.
 * @param address     O endereço da empresa. Pode ser nulo.
 * @param phoneNumber O número de telefone da empresa. Pode ser nulo.
 * @param email       O e-mail da empresa. Pode ser nulo.
 * @param urlImage    A URL da imagem da empresa. Pode ser nulo.
 */
public record UpdateRequest(
    String name,
    String document,
    String address,
    String phoneNumber,
    String email,
    String urlImage
) {}