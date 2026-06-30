package com.github.sergiohrvas.domain.models.valueobjects;

import java.util.List;
import java.util.Objects;

import com.github.sergiohrvas.domain.exceptions.InvalidQuery;

public record Query (
    String query
) {
    private static final List<String> FORBIDDEN_COMMANDS = List.of(
        "insert", "update", "delete", "alter", "drop", 
        "truncate", "create", "grant", "revoke", "lock", "unlock"
    );

    private static final int MAX_QUERY_LENGTH = 1000;
    
    public Query (String query) {
        if (Objects.isNull(query)) {
            throw new InvalidQuery("Query cannot be null");
        }

        String normalizedQuery = query.trim().toLowerCase();
        if (normalizedQuery.isEmpty()) {
            throw new InvalidQuery("Query cannot be empty");
        }

        if (normalizedQuery.length() > MAX_QUERY_LENGTH) {
            throw new InvalidQuery("Query cannot be longer than " + MAX_QUERY_LENGTH + " characters");
        }

        if (FORBIDDEN_COMMANDS.stream().anyMatch(normalizedQuery::contains)) {
            throw new InvalidQuery("Query cannot contain modifying statements");
        }

        this.query = normalizedQuery;
    }    
}
