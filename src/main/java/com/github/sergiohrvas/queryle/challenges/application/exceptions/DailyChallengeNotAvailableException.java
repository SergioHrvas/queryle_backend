package com.github.sergiohrvas.queryle.challenges.application.exceptions;

import java.util.UUID;

public class DailyChallengeNotAvailableException extends RuntimeException {

    public DailyChallengeNotAvailableException(UUID dailyChallengeId) {
        super("Daily challenge not available for id " + dailyChallengeId);
    }
}