package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.mappers;

import java.util.Objects;

import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Feedback;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Attempt;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response.AttemptResponseDTO;

public class AttemptResponseMapper {
    public static AttemptResponseDTO toDTO(Attempt attempt) {
        if (Objects.isNull(attempt)) return null;

        return new AttemptResponseDTO(
            attempt.query().value(),
            FeedbackResponseMapper.toDTO(attempt.feedback()),
            attempt.date()
        );
    }
}
