package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.exceptions;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.github.sergiohrvas.queryle.challenges.application.exceptions.DailyChallengeNotFoundException;
import com.github.sergiohrvas.queryle.challenges.application.exceptions.DataContextNotFoundException;
import com.github.sergiohrvas.queryle.challenges.domain.exceptions.InvalidDataContextException;
import com.github.sergiohrvas.queryle.challenges.domain.exceptions.InvalidQueryException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(InvalidDataContextException.class)
    public ProblemDetail handleDomainException(InvalidDataContextException ex) {
        return problem(
            HttpStatus.UNPROCESSABLE_CONTENT,
            "Domain rule violated",
            ex.getMessage(),
            "domain-rule-violated"
        );
    }

    @ExceptionHandler (InvalidQueryException.class)
    public ProblemDetail handleInvalidQueryException(InvalidQueryException ex) {
        return problem(
            HttpStatus.UNPROCESSABLE_CONTENT,
            "Invalid query",
            ex.getMessage(),
            "invalid-query"
        );
    }

    @ExceptionHandler({
        DailyChallengeNotFoundException.class,
        DataContextNotFoundException.class
    })
    public ProblemDetail handleNotFoundException(RuntimeException ex) {
        return problem(
            HttpStatus.NOT_FOUND,
            "Resource not found",
            ex.getMessage(),
            "resource-not-found"
        );
    }

    @ExceptionHandler({
        MethodArgumentNotValidException.class,
        MissingRequestHeaderException.class,
        MissingServletRequestParameterException.class,
        MethodArgumentTypeMismatchException.class,
        HttpMessageNotReadableException.class
    })
    public ProblemDetail handleBadRequest(Exception ex) {
        return problem(
            HttpStatus.BAD_REQUEST,
            "Invalid request",
            "The request could not be understood or contains invalid values.",
            "invalid-request"
        );
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnexpectedException(Exception ex) {
        LOGGER.error("Unexpected error while processing REST request", ex);
        return problem(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Internal server error",
            "An unexpected error occurred.",
            "internal-server-error"
        );
    }

    private ProblemDetail problem(
        HttpStatus status,
        String title,
        String detail,
        String type
    ) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
        problemDetail.setTitle(title);
        problemDetail.setType(URI.create(type));
        return problemDetail;
    }
}