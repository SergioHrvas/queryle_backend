package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response;

import java.util.List;

public record DatasetTableResponseDTO(
    String name,
    List<String> columns,
    List<List<Object>> rows
) {
}
