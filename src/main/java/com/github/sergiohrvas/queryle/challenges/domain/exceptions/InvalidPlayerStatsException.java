package com.github.sergiohrvas.queryle.challenges.domain.exceptions;

public class InvalidPlayerStatsException extends RuntimeException {
    public InvalidPlayerStatsException(String message) {
        super(message);
    }
}
