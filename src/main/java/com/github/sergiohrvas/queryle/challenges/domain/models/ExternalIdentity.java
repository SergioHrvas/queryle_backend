package com.github.sergiohrvas.queryle.challenges.domain.models;

import java.util.Objects;

public record ExternalIdentity(String issuer, String subject) {
    public ExternalIdentity {
        if (Objects.isNull(issuer) || issuer.isBlank()) {
            throw new IllegalArgumentException("Identity issuer cannot be null or blank");
        }
        if (Objects.isNull(subject) || subject.isBlank()) {
            throw new IllegalArgumentException("Identity subject cannot be null or blank");
        }
    }
}
