package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.request;

import java.util.List;

public record CreateDataContextRequestDTO(
    String name,
    String description,
    String schema,
    String publicDataset,
    List<String> privateDatasets
) {
}
