package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response;

import java.util.UUID;

public record DailyChallengeResponseDTO(
    UUID id,
    String formulation,
    UUID dataContextId,
    int sequence
) {
}
