package com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects;

import java.util.Objects;
import java.util.regex.Pattern;

import com.github.sergiohrvas.queryle.challenges.domain.exceptions.InvalidDatasetException;

public record Dataset(String dataFixture) {

    private static final Pattern FORBIDDEN_STATEMENTS = Pattern.compile(
        "\\b(delete|alter|drop|truncate|create|grant|revoke|lock|unlock)\\b",
        Pattern.CASE_INSENSITIVE
    );

    public Dataset {
        if (Objects.isNull(dataFixture) || dataFixture.trim().isBlank()) {
            throw new InvalidDatasetException(
                "Dataset cannot be null or empty"
            );
        }

        dataFixture = dataFixture.trim();

        if (FORBIDDEN_STATEMENTS.matcher(dataFixture).find()) {
            throw new InvalidDatasetException(
                "Dataset contains forbidden statements"
            );
        }
    }
}