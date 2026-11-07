package com.github.sergiohrvas.queryle.challenges.domain.exceptions;

/**
 * Exception thrown when a query result is invalid.
 */
public class InvalidQueryResult extends RuntimeException {
    public InvalidQueryResult(String message) {
        super(message);
    }
}
