package com.dellasse.backend.contracts.party;

public record PartyUpdateRequest (
    String title,
    String description, 
    String observations, 
    String lastAtualization,  
    String imageURL, 
    Double generateBudget
) {}

