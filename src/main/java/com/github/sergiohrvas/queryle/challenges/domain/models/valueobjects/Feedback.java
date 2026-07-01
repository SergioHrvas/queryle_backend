package com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects;

import java.util.Objects;

import com.github.sergiohrvas.queryle.challenges.domain.exceptions.InvalidFeedbackException;

public record Feedback (
    MatchLevel columnsMatchLevel,
    MatchLevel rowsMatchLevel,
    MatchLevel dataMatchLevel,
    MatchLevel orderMatchLevel
) {
    public Feedback {
        if(Objects.isNull(columnsMatchLevel)){
            throw new InvalidFeedbackException("Columns match level cannot be null");
        }
        if(Objects.isNull(rowsMatchLevel)){
            throw new InvalidFeedbackException("Rows match level cannot be null");
        }
        if(Objects.isNull(dataMatchLevel)){
            throw new InvalidFeedbackException("Data match level cannot be null");
        }
        if(Objects.isNull(orderMatchLevel)){
            throw new InvalidFeedbackException("Order match level cannot be null");
        }
    }
}
