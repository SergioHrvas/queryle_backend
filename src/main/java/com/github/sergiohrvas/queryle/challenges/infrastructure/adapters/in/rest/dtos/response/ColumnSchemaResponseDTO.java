package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response;

public record ColumnSchemaResponseDTO(
    String name,
    String type,
    boolean nullable,
    boolean primaryKey,
    int position
) {
}
