package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response;

public class QueryEvaluationResponseDTO {
    private final FeedbackResponseDTO feedback;
    private final boolean validationPassed;

    public QueryEvaluationResponseDTO(FeedbackResponseDTO feedback, boolean validationPassed) {
        this.feedback = feedback;
        this.validationPassed = validationPassed;
    }

    public FeedbackResponseDTO getFeedback() {
        return feedback;
    }

    public boolean isValidationPassed() {
        return validationPassed;
    }
}
