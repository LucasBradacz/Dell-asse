package com.dellasse.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dellasse.backend.contracts.party.PartyCreateRequest;
import com.dellasse.backend.models.Party;
import com.dellasse.backend.service.PartyService;

import jakarta.validation.Valid;

/**
 * @author Equipe Compilador 
 * @version 1.0
 * Controlador para gerenciar operações relacionadas a festas.
 * - A operação inclui a criação de festas.
 * - As operações são protegidas e requerem autenticação do usuário.
 * - Retorna a entidade Party criada.
 * - Utiliza o serviço PartyService para realizar a lógica de negócio.
 * - Recebe requisições HTTP POST para criar uma nova festa.
 */
@RestController
@RequestMapping("party")
public class PartyController {

    @Autowired
    private PartyService partyService;

    @PostMapping("/create")
    public Party create(@Valid @RequestBody PartyCreateRequest createRequest, JwtAuthenticationToken token){
        return partyService.create(createRequest, token.getName());
    }
}
