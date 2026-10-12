package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.sql.exceptions;

public class SqlQueryExecutionException extends RuntimeException {

    public SqlQueryExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}