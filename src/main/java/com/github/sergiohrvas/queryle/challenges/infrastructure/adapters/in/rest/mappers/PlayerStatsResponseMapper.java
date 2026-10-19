package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.mappers;

import java.util.Objects;

import com.github.sergiohrvas.queryle.challenges.application.queries.getplayerstats.PlayerStatsReadModel;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response.PlayerStatsResponseDTO;

public class PlayerStatsResponseMapper {
    public static PlayerStatsResponseDTO toDTO(PlayerStatsReadModel playerStats) {
        if (Objects.isNull(playerStats)) return null;

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
