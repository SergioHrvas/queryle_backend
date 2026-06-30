package com.github.sergiohrvas.domain.exceptions;

public class InvalidQuery extends RuntimeException {
    
    public InvalidQuery(String message) {
        super("Invalid query: " + message);
    }

    public InvalidQuery(String message, Throwable cause) {
        super("Invalid query: " + message, cause);
    }
}
