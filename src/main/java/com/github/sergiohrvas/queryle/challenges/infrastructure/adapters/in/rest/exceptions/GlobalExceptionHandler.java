package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.github.sergiohrvas.queryle.challenges.application.exceptions.DailyChallengeNotFoundException;
import com.github.sergiohrvas.queryle.challenges.domain.exceptions.DomainExcepcion;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DomainExcepcion.class)
    public ProblemDetail handleDomainExcepcion(DomainExcepcion ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_CONTENT);
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(DailyChallengeNotFoundException.class)
    public ProblemDetail handleDailyChallengeNotFoundException(DailyChallengeNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }
}