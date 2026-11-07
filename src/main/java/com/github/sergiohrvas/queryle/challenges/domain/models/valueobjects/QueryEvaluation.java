package com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects;

import java.util.Objects;

import com.github.sergiohrvas.queryle.challenges.domain.exceptions.InvalidQueryEvaluationException;

public record QueryEvaluation (Feedback feedback, boolean validationPassed) {
    public QueryEvaluation {
        if (Objects.isNull(feedback)) {
            throw new InvalidQueryEvaluationException("Feedback cannot be null");
        }
    }
}
