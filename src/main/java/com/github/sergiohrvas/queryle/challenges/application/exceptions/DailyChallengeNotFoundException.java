package com.github.sergiohrvas.queryle.challenges.application.exceptions;

import java.time.LocalDate;
import java.util.UUID;

import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Difficulty;

public class DailyChallengeNotFoundException extends RuntimeException {

    public DailyChallengeNotFoundException(UUID dailyChallengeId) {
        super("Daily challenge not found for id " + dailyChallengeId);
    }

    public DailyChallengeNotFoundException(LocalDate publicationDate, Difficulty difficulty) {
        super("Daily challenge not found for publication date " + publicationDate + " and difficulty " + difficulty);
    }
}
