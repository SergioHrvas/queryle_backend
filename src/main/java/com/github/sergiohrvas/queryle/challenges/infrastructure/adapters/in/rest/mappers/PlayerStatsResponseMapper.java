package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.mappers;

import com.github.sergiohrvas.queryle.challenges.application.queries.getplayerstats.PlayerStatsView;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response.PlayerStatsResponseDTO;

public final class PlayerStatsResponseMapper {
    private PlayerStatsResponseMapper() {}
    public static PlayerStatsResponseDTO toDTO(PlayerStatsView playerStats) {
        return new PlayerStatsResponseDTO(
            playerStats.playerId(),
            playerStats.playedGames(),
            playerStats.wonGames(),
            playerStats.lostGames(),
            playerStats.currentStreak(),
            playerStats.longestStreak()
        );
    }
}
