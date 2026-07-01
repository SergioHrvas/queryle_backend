package com.github.sergiohrvas.queryle.challenges.domain.exceptions;

public class InvalidGameException extends RuntimeException {
    public InvalidGameException(String message){
        super(message);
    }
}
