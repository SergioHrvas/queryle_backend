package com.github.sergiohrvas.queryle.challenges.application.exceptions;

import java.util.UUID;

public class DailyChallengeNotFoundException extends RuntimeException {

    public DailyChallengeNotFoundException(UUID dailyChallengeId) {
        super("Daily challenge not found for id " + dailyChallengeId);
    }
}
