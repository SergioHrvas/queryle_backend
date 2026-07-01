package com.github.sergiohrvas.queryle.challenges.domain.exceptions;

public class InvalidAttemptException extends RuntimeException {
    public InvalidAttemptException(String message) {
        super(message);
    }
}
