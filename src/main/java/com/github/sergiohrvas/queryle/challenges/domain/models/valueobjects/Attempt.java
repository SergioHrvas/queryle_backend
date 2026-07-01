package com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects;

import java.time.LocalDateTime;
import java.util.Objects;

import com.github.sergiohrvas.queryle.challenges.domain.exceptions.InvalidAttemptException;

public record Attempt(Query query, boolean isCorrect, LocalDateTime date) {
    public Attempt {
        if (Objects.isNull(query)) {
            throw new InvalidAttemptException("Query cannot be null");
        }

        if(Objects.isNull(date)){
            throw new InvalidAttemptException("Date cannot be null");
        }
    }
}
