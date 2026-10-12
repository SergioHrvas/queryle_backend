package com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects;

import java.util.Objects;
import java.util.regex.Pattern;

import com.github.sergiohrvas.queryle.challenges.domain.exceptions.InvalidSeedScriptException;

public record SeedScript (String schema, String dataFixture){ 
    private static final Pattern FORBIDDEN_IN_SCHEMA = Pattern.compile(
        "\\b(insert|update|delete|drop|truncate|grant|revoke|lock|unlock)\\b", 
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern FORBIDDEN_IN_FIXTURE = Pattern.compile(
        "\\b(delete|alter|drop|truncate|create|grant|revoke|lock|unlock)\\b", 
        Pattern.CASE_INSENSITIVE
    );

    public SeedScript {
        if (Objects.isNull(schema)) {
            throw new InvalidSeedScriptException("Schema cannot be null");
        }
        if(schema.trim().isBlank()){
            throw new InvalidSeedScriptException("Schema cannot be empty");
        }
        if(Objects.isNull(dataFixture)){
            throw new InvalidSeedScriptException("Data fixture cannot be null");
        }
        if(dataFixture.trim().isBlank()){
            throw new InvalidSeedScriptException("Data fixture cannot be empty");
        }

        schema = schema.trim();
        dataFixture = dataFixture.trim();

        if (FORBIDDEN_IN_SCHEMA.matcher(schema).find()) {
            throw new InvalidSeedScriptException("Schema cannot contain modifying statements");
        }
        if (FORBIDDEN_IN_FIXTURE.matcher(dataFixture).find()) {
            throw new InvalidSeedScriptException("Data fixture cannot contain modifying statements");
        }
    }
}
