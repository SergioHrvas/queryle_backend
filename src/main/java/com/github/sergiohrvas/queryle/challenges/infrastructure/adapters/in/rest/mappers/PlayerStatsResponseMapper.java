package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.mappers;

import java.util.Objects;

import com.github.sergiohrvas.queryle.challenges.domain.models.PlayerStats;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response.PlayerStatsResponseDTO;

public class PlayerStatsResponseMapper {
    public static PlayerStatsResponseDTO toDTO(PlayerStats playerStats) {
        if (Objects.isNull(playerStats)) return null;

        return new PlayerStatsResponseDTO(
            playerStats.getPlayerId(),
            playerStats.getPlayedGames(),
            playerStats.getWonGames(),
            playerStats.getLostGames(),
            playerStats.getCurrentStreak(),
            playerStats.getLongestStreak(),
            playerStats.getWinRate(),
            playerStats.getLossRate()
        );
    }
}
