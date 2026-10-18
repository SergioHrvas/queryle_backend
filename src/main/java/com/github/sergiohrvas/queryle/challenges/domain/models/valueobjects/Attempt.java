package com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects;

import java.time.LocalDateTime;
import java.util.Objects;

import com.github.sergiohrvas.queryle.challenges.domain.exceptions.InvalidAttemptException;

public record Attempt(Query query, QueryEvaluation evaluation, LocalDateTime submittedAt) {
    public Attempt {
        if (Objects.isNull(query)) {
            throw new InvalidAttemptException("Query cannot be null");
        }
        if(Objects.isNull(submittedAt)){
            throw new InvalidAttemptException("Submitted date cannot be null");
        }
        if(Objects.isNull(evaluation)) {
            throw new InvalidAttemptException("Query evaluation cannot be null");
        }
    }

    public boolean isCorrect() {
        return evaluation.validationPassed() && evaluation.feedback().isAllExact();
    }
}
