package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.game.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

@Entity
@Table(name = "games")
public class GameEntity {
    
    @Id
    private UUID id;

    @Column(name = "player_id", nullable = false)
    private UUID playerId;

    @Column(name = "daily_challenge_id", nullable = false)
    private UUID dailyChallengeId;

    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    @Column(name = "status", nullable = false)
    private String status;

    @OrderBy("submittedAt ASC")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "game")
    private List<AttemptEntity> attempts;

    public GameEntity(UUID id, UUID playerId, UUID dailyChallengeId, LocalDateTime startedAt, String status, List<AttemptEntity> attempts) {
        this.id = id;
        this.playerId = playerId;
        this.dailyChallengeId = dailyChallengeId;
        this.startedAt = startedAt;
        this.status = status;
        this.attempts = attempts;
    }
    
    protected GameEntity() {}

    public void addAttempt(AttemptEntity attempt) {
        attempts.add(attempt);
    }

    public UUID getId() {
        return id;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public UUID getDailyChallengeId() {
        return dailyChallengeId;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public String getStatus() {
        return status;
    }

    public List<AttemptEntity> getAttempts() {
        return attempts;
    }
}
