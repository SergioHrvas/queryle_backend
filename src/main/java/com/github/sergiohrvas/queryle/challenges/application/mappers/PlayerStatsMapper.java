package com.github.sergiohrvas.queryle.challenges.application.mappers;

import java.util.Objects;

import com.github.sergiohrvas.queryle.challenges.application.dtos.PlayerStatsResponseDTO;
import com.github.sergiohrvas.queryle.challenges.domain.models.PlayerStats;

public class PlayerStatsMapper {
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
