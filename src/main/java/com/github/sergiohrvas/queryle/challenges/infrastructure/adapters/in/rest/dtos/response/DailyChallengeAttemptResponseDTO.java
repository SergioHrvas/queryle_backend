package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record DailyChallengeAttemptResponseDTO(
    UUID id,
    String query,
    LocalDateTime submittedAt,
    boolean validationPassed,
    String columnsNamesMatchLevel,
    String columnsCountMatchLevel,
    String rowsCountMatchLevel,
    String rowsDataMatchLevel,
    String rowsOrderMatchLevel
) {
}
