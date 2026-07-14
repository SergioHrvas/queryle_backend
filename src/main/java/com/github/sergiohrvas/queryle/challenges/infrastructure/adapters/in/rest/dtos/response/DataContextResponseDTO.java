package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response;

import java.util.UUID;

public record DataContextResponseDTO(
    UUID id,
    String name,
    String description,
    String seedScript
) {
}
