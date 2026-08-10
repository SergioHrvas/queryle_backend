package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.mappers;

import java.util.Objects;

import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Feedback;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response.FeedbackResponseDTO;

public class FeedbackResponseMapper {
    public static FeedbackResponseDTO toDTO(Feedback feedback) {
        if (Objects.isNull(feedback)) return null;

        return new FeedbackResponseDTO(
            feedback.columnsNamesMatchLevel(),
            feedback.columnsCountMatchLevel(),
            feedback.rowsCountMatchLevel(),
            feedback.rowsDataMatchLevel(),
            feedback.rowsOrderMatchLevel()
        );
    }
}
