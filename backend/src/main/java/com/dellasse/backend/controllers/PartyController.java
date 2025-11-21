package com.dellasse.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dellasse.backend.contracts.party.PartyCreateRequest;
import com.dellasse.backend.contracts.party.PartyResponse;
import com.dellasse.backend.models.Party;
import com.dellasse.backend.service.PartyService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("party")
public class PartyController {

    @Autowired
    private PartyService partyService;

    @PostMapping("/create")
    public Party create(@Valid @RequestBody PartyCreateRequest createRequest, JwtAuthenticationToken token){
        return partyService.create(createRequest, token.getName());
    }

    @PatchMapping("/{id}")
    public PartyResponse update(@PathVariable Long id, @Valid @RequestBody PartyCreateRequest updateRequest, JwtAuthenticationToken token){
        return partyService.update(id, updateRequest, token.getName());
    }

    @GetMapping("/{id}")
    public PartyResponse getById(@PathVariable Long id, JwtAuthenticationToken token){
        return partyService.getById(id, token.getName());
    }

    @GetMapping("/all")
    public List<PartyResponse> getAll(JwtAuthenticationToken token){
        return partyService.getAll(token.getName());
    }
}
