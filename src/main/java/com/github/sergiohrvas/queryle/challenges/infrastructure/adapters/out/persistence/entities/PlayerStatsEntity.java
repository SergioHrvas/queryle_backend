package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.entities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "player_stats")
public class PlayerStatsEntity {

    @Id
    @Column(name = "player_id", nullable = false, updatable = false)
    private UUID playerId;

    @Column(name = "played_games", nullable = false)
    private int playedGames;

    @Column(name = "won_games", nullable = false)
    private int wonGames;

    @Column(name = "lost_games", nullable = false)
    private int lostGames;

    @Column(name = "current_streak", nullable = false)
    private int currentStreak;

    @Column(name = "longest_streak", nullable = false)
    private int longestStreak;

    @Column(name = "last_challenge_sequence", nullable = false)
    private int lastChallengeSequence;
    
    protected PlayerStatsEntity() { }

    public PlayerStatsEntity(UUID playerId, int playedGames, int wonGames, int lostGames, int currentStreak, int longestStreak, int lastChallengeSequence) {
        this.playerId = playerId;
        this.playedGames = playedGames;
        this.wonGames = wonGames;
        this.lostGames = lostGames;
        this.currentStreak = currentStreak;
        this.longestStreak = longestStreak;
        this.lastChallengeSequence = lastChallengeSequence;
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
    
    public int getLastChallengeSequence() {
        return lastChallengeSequence;
    }
}