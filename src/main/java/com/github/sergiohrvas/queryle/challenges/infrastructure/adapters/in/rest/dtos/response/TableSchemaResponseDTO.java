package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response;

import java.util.List;

public record TableSchemaResponseDTO(
    String name,
    List<ColumnSchemaResponseDTO> columns
) {
}
