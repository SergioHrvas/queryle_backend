package com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects;

import java.util.Objects;
import java.util.regex.Pattern;

import com.github.sergiohrvas.queryle.challenges.domain.exceptions.InvalidSchemaException;

public record Schema(String value) {

    private static final Pattern FORBIDDEN_STATEMENTS = Pattern.compile(
        "\\b(insert|update|delete|drop|truncate|grant|revoke|lock|unlock)\\b",
        Pattern.CASE_INSENSITIVE
    );

    public Schema {
        if (Objects.isNull(value) || value.trim().isBlank()) {
            throw new InvalidSchemaException("Schema cannot be null or empty");
        }

        value = value.trim();

        if (FORBIDDEN_STATEMENTS.matcher(value).find()) {
            throw new InvalidSchemaException(
                "Schema contains forbidden statements"
            );
        }
    }
}