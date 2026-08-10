package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.mappers;

import java.util.Objects;

import com.github.sergiohrvas.queryle.challenges.domain.models.PlayerStats;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.entities.PlayerStatsEntity;

public class PlayerStatsEntityMapper {
    public static PlayerStatsEntity toEntity(PlayerStats playerStats) {
        if (Objects.isNull(playerStats)) {
            return null;
        }

        return new PlayerStatsEntity(
            playerStats.getPlayerId(),
            playerStats.getPlayedGames(),
            playerStats.getWonGames(),
            playerStats.getLostGames(),
            playerStats.getCurrentStreak(),
            playerStats.getLongestStreak(),
            playerStats.getLastChallengeSequence()
        );
    }

    public static PlayerStats toDomain(PlayerStatsEntity playerStatsEntity) {
        if (Objects.isNull(playerStatsEntity)) {
            return null;
        }

        return new PlayerStats(
            playerStatsEntity.getPlayerId(),
            playerStatsEntity.getPlayedGames(),
            playerStatsEntity.getWonGames(),
            playerStatsEntity.getLostGames(),
            playerStatsEntity.getCurrentStreak(),
            playerStatsEntity.getLongestStreak(),
            playerStatsEntity.getLastChallengeSequence()
        );
    }
}
