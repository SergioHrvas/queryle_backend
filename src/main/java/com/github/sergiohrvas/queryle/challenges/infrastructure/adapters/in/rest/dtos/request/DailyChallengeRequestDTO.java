package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.request;

import java.time.LocalDate;
import java.util.UUID;

public record DailyChallengeRequestDTO(
    String formulation,
    String query,
    String difficulty,
    LocalDate publicationDate,
    String icon,
    UUID dataContextId
) {
}