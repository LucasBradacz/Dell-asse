package com.dellasse.backend.contracts.party;

public record PartyResponse(

    Long id,
    String title,
    String observation,
    String budget,
    String imageUrl,
    String status

) {}
