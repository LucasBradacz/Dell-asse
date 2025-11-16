package com.dellasse.backend.contracts.party;

import java.util.List;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PartyCreateRequest(

    @NotBlank(message = "Title is required")
    String title,

    @NotBlank(message = "Description is required")
    String description,

    List<Long> productList,

    @NotNull(message = "Generate Budget is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Generate Budget must be zero or greater")
    Double generateBudget,

    String observations,
    String imageURL

) {}


