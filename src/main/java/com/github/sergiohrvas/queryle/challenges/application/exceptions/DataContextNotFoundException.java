package com.github.sergiohrvas.queryle.challenges.application.exceptions;

import java.util.UUID;

public class DataContextNotFoundException extends RuntimeException {

    public DataContextNotFoundException(UUID dailyChallengeId) {
        super("Data context not found for id " + dailyChallengeId);
    }
}
