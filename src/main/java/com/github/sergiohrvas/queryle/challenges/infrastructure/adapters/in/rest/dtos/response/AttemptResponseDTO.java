package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response;

import java.time.LocalDateTime;

public record AttemptResponseDTO(
    String query,
    FeedbackResponseDTO feedback,
    LocalDateTime submittedDate
) {
}