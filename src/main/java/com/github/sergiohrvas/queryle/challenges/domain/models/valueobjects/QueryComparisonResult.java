package com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects;

public record QueryComparisonResult(
    QueryResult expected,
    QueryResult actual
) {
}
