package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response;

public record DailyChallengeGameResponseDTO(
    DailyChallengeResponseDTO dailyChallenge,
    GameResponseDTO game
) {
}
