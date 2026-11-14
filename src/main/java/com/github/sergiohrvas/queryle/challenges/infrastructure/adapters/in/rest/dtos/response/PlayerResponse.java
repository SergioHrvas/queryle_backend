package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response;

public record PlayerResponse(
    java.util.UUID id,
    String username,
    String email,
    String identitySubject
) {
}