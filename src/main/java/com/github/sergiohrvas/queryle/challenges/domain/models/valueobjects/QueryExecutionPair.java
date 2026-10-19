package com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects;

public record QueryExecutionPair(
    QueryResult expected,
    QueryResult actual
) {
}
