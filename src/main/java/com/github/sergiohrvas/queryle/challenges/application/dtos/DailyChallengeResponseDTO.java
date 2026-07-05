package com.github.sergiohrvas.queryle.challenges.application.dtos;

import java.util.UUID;

public record DailyChallengeResponseDTO(
    UUID id,
    String formulation,
    UUID dataContextId,
    int sequence
) {
}
