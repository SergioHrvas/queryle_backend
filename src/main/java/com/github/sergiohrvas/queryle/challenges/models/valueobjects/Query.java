package com.github.sergiohrvas.queryle.challenges.models.valueobjects;

import java.util.List;
import java.util.Objects;

import com.github.sergiohrvas.queryle.challenges.domain.exceptions.InvalidQueryException;

public record Query (
    String value
) {
    private static final List<String> FORBIDDEN_COMMANDS = List.of(
        "insert", "update", "delete", "alter", "drop", 
        "truncate", "create", "grant", "revoke", "lock", "unlock"
    );

    private static final int MAX_QUERY_LENGTH = 1000;
    
    public Query (String value) {
        if (Objects.isNull(value)) {
            throw new InvalidQueryException("Query cannot be null");
        }

        String normalizedQuery = value.trim().toLowerCase();
        if (normalizedQuery.isEmpty()) {
            throw new InvalidQueryException("Query cannot be empty");
        }

        if (normalizedQuery.length() > MAX_QUERY_LENGTH) {
            throw new InvalidQueryException("Query cannot be longer than " + MAX_QUERY_LENGTH + " characters");
        }

        if (FORBIDDEN_COMMANDS.stream().anyMatch(normalizedQuery::contains)) {
            throw new InvalidQueryException("Query cannot contain modifying statements");
        }

        this.value = normalizedQuery;
    }    
}
