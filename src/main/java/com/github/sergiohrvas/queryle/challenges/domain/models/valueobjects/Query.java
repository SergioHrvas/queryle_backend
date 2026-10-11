package com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects;

import java.util.Objects;
import java.util.regex.Pattern;

import com.github.sergiohrvas.queryle.challenges.domain.exceptions.InvalidQueryException;

public record Query (String value) {
    private static final Pattern FORBIDDEN_PATTERN = Pattern.compile(
        "\\b(insert|update|delete|alter|drop|truncate|create|grant|revoke|lock|unlock)\\b", 
        Pattern.CASE_INSENSITIVE
    );

    private static final int MAX_QUERY_LENGTH = 1000;
    
    public Query {
        if (Objects.isNull(value)) {
            throw new InvalidQueryException("Value text cannot be null");
        }

        value = value.trim();
        if (value.isBlank()) {
            throw new InvalidQueryException("Value text cannot be empty");
        }

        if (value.length() > MAX_QUERY_LENGTH) {
            throw new InvalidQueryException("Query cannot be longer than " + MAX_QUERY_LENGTH + " characters");
        }

        if (FORBIDDEN_PATTERN.matcher(value).find()) {
            throw new InvalidQueryException("Query cannot contain modifying statements");
        }
    }    
}