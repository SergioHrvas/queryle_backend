package com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects;

import java.time.LocalDateTime;
import java.util.Objects;

import com.github.sergiohrvas.queryle.challenges.domain.exceptions.InvalidAttemptException;

public record Attempt(Query query, Feedback feedback, LocalDateTime date) {
    public Attempt {
        if (Objects.isNull(query)) {
            throw new InvalidAttemptException("Query cannot be null");
        }

        if(Objects.isNull(date)){
            throw new InvalidAttemptException("Date cannot be null");
        }

        if(Objects.isNull(feedback)) {
            throw new InvalidAttemptException("Feedback cannot be null");
        }
    }

    public boolean isCorrect() {
        return feedback.isAllExact();
    }
}
