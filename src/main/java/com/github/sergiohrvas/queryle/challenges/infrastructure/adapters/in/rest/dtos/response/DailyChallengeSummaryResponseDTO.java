package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response;

import java.time.LocalDate;
import java.util.UUID;

public record DailyChallengeSummaryResponseDTO(
    UUID id,
    String formulation,
    LocalDate publicationDate,
    String status
) {
}
