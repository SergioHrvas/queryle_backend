package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response;

import java.util.List;

public record DailyChallengeGameResponseDTO(
    String status,
    List<DailyChallengeAttemptResponseDTO> attempts
) {
}
