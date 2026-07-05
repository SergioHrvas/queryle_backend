package com.github.sergiohrvas.queryle.challenges.domain.models;

import java.util.Objects;
import java.util.UUID;

import com.github.sergiohrvas.queryle.challenges.domain.exceptions.InvalidPlayerStatsException;

public class PlayerStats {
    private final UUID playerId;
    private final int playedGames;
    private final int wonGames;
    private final int lostGames;
    private final int currentStreak;
    private final int longestStreak;

    public PlayerStats(int playedGames, int wonGames, int lostGames, int currentStreak, int longestStreak) {
        this(UUID.randomUUID(), playedGames, wonGames, lostGames, currentStreak, longestStreak);
    }

    public PlayerStats(UUID playerId, int playedGames, int wonGames, int lostGames, int currentStreak, int longestStreak) {
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

    public UUID getPlayerId() {
        return playerId;
    }

    public int getPlayedGames() {
        return playedGames;
    }

    public int getWonGames() {
        return wonGames;
    }

    public int getLostGames() {
        return lostGames;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public int getLongestStreak() {
        return longestStreak;
    }

    public double getWinRate() {
        return wonGames / playedGames;
    }

    public double getLossRate() {
        return lostGames / playedGames;
    }

    @Override
    public String toString() {
        return "PlayerStats{" +
                "playerId=" + playerId +
                ", playedGames=" + playedGames +
                ", wonGames=" + wonGames +
                ", lostGames=" + lostGames +
                ", currentStreak=" + currentStreak +
                ", longestStreak=" + longestStreak +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerStats that = (PlayerStats) o;
        return playedGames == that.playedGames && wonGames == that.wonGames && lostGames == that.lostGames && currentStreak == that.currentStreak && longestStreak == that.longestStreak && Objects.equals(playerId, that.playerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId, playedGames, wonGames, lostGames, currentStreak, longestStreak);
    }
}