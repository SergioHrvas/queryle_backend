package com.github.sergiohrvas.queryle.challenges.application.dtos;

import java.util.UUID;

public record PlayerStatsResponseDTO(
    UUID playerId,
    int playedGames,
    int wonGames,
    int lostGames,
    int currentStreak,
    int longestStreak,
    double winRate,
    double lossRate
) {
}
