package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response;

import java.util.List;

public record SchemaResponseDTO(
    List<TableSchemaResponseDTO> tables
) {
}
