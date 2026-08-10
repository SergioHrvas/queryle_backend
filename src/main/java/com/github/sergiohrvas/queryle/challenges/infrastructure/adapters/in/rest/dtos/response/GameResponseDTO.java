package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.GameStatus;

public record GameResponseDTO(
    UUID id,
    LocalDateTime startedDate,
    GameStatus status,
    List<AttemptResponseDTO> attempts
) {
}
