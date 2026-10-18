package com.github.sergiohrvas.queryle.challenges.application.dtos;

import java.util.Objects;
import java.util.UUID;

import com.github.sergiohrvas.queryle.challenges.domain.exceptions.InvalidPlayerStatsException;

public record PlayerStatsReadModel (
    UUID playerId,
    int playedGames,
    int wonGames,
    int lostGames,
    int currentStreak,
    int longestStreak
){
    public PlayerStatsReadModel(UUID playerId, int playedGames, int wonGames, int lostGames, int currentStreak, int longestStreak) {
        validateState(playerId, playedGames, wonGames, lostGames, currentStreak, longestStreak);
        
        this.playerId = playerId;
        this.playedGames = playedGames;
        this.wonGames = wonGames;
        this.lostGames = lostGames;
        this.currentStreak = currentStreak;
        this.longestStreak = longestStreak;
    }

    private void validateState(UUID playerId, int playedGames, int wonGames, int lostGames, int currentStreak, int longestStreak) {
        if (Objects.isNull(playerId)) {
            throw new InvalidPlayerStatsException("Player ID cannot be null");
        }

        if (playedGames < 0) {
            throw new InvalidPlayerStatsException("Played games cannot be negative");
        }

        if (wonGames < 0) {
            throw new InvalidPlayerStatsException("Won games cannot be negative");
        }

        if (lostGames < 0) {
            throw new InvalidPlayerStatsException("Lost games cannot be negative");
        }
        
        if (currentStreak < 0) {
            throw new InvalidPlayerStatsException("Current streak cannot be negative");
        }

        if (longestStreak < 0) {
            throw new InvalidPlayerStatsException("Longest streak cannot be negative");
        }
        
        if (currentStreak > longestStreak) {
            throw new InvalidPlayerStatsException("Current streak cannot be greater than longest streak");
        }

        if (wonGames + lostGames != playedGames) {
            throw new InvalidPlayerStatsException("Won games plus lost games must equal played games");
        }

    }
}