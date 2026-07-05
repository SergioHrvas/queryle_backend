package com.github.sergiohrvas.queryle.challenges.application.dtos;

import java.util.UUID;

public record DataContextResponseDTO(
    UUID id,
    String name,
    String description,
    String seedScript
) {
}
