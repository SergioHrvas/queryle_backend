package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.mappers;

import java.util.Objects;

import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.QueryEvaluation;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response.QueryEvaluationResponseDTO;

public class QueryEvaluationResponseMapper {
    public static QueryEvaluationResponseDTO toDTO(QueryEvaluation queryEvaluation) {
        if (Objects.isNull(queryEvaluation)) return null;

        return new QueryEvaluationResponseDTO(
            FeedbackResponseMapper.toDTO(queryEvaluation.feedback()),
            queryEvaluation.validationPassed()
        );
    }
}
