package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response;

import java.time.LocalDate;
import java.util.UUID;

public record DailyChallengeDetailResponseDTO(
    UUID id,
    String formulation,
    String difficulty,
    LocalDate publicationDate,
    DailyChallengeGameResponseDTO game,
    DataContextResponseDTO dataContext
) {
}
