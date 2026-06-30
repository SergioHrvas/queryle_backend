package com.github.sergiohrvas.queryle.challenges.domain.exceptions;

public class InvalidQueryException extends RuntimeException {
    public InvalidQueryException(String message) {
        super(message);
    }
}
