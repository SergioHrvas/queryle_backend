package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response;

public record DataContextResponseDTO(
    String name,
    String description,
    SchemaResponseDTO schema,
    DatasetResponseDTO dataset
) {
}