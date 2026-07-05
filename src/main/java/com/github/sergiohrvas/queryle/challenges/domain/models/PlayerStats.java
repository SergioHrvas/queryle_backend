package com.github.sergiohrvas.queryle.challenges.domain.models;

import java.util.Objects;
import java.util.UUID;

import com.github.sergiohrvas.queryle.challenges.domain.exceptions.InvalidPlayerStatsException;

public class PlayerStats {
    private final UUID playerId;
    private int playedGames;
    private int wonGames;
    private int lostGames;
    private int currentStreak;
    private int longestStreak;
    private int lastChallengeSequence;

    public PlayerStats(UUID playerId) {
        this(playerId, 0, 0, 0, 0, 0, 0);
    }

    public PlayerStats(UUID playerId, int playedGames, int wonGames, int lostGames, int currentStreak, int longestStreak, int lastChallengeSequence) {
        validateState(playerId, playedGames, wonGames, lostGames, currentStreak, longestStreak, lastChallengeSequence);
        
        this.playerId = playerId;
        this.playedGames = playedGames;
        this.wonGames = wonGames;
        this.lostGames = lostGames;
        this.currentStreak = currentStreak;
        this.longestStreak = longestStreak;
        this.lastChallengeSequence = lastChallengeSequence;
    }


    private void validateState(UUID playerId, int playedGames, int wonGames, int lostGames, int currentStreak, int longestStreak, int lastChallengeSequence) {
        if (Objects.isNull(playerId)) {
            throw new InvalidPlayerStatsException("Player ID cannot be null");
        }
        
        if (lastChallengeSequence < 0) {
            throw new InvalidPlayerStatsException("Last challenge sequence cannot be negative");
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

    public void recordGameResult(boolean isWon, int newChallengeSequence, boolean isChallengeOfToday) {
        playedGames++;

        if (!isWon) {
            this.lostGames++;
        } else {
            this.wonGames++;
        }

        if (isChallengeOfToday) {
            if (!isWon) {
                this.currentStreak = 0;
                return;
            }

            if (this.lastChallengeSequence != 0 && this.lastChallengeSequence + 1 != newChallengeSequence) {
                this.currentStreak = 0;
            }

            this.currentStreak++;

            if (this.currentStreak > this.longestStreak) {
                this.longestStreak = this.currentStreak;
            }

            this.lastChallengeSequence = newChallengeSequence;
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
        if (playedGames == 0) {
            return 0.0;
        }
        return (double) wonGames / playedGames;
    }

    public double getLossRate() {
        if (playedGames == 0) {
            return 0.0;
        }
        return (double) lostGames / playedGames;
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
                ", lastChallengeSequence=" + lastChallengeSequence +
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