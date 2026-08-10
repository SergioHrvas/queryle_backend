package com.github.sergiohrvas.queryle.challenges.domain.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.github.sergiohrvas.queryle.challenges.domain.exceptions.InvalidGameException;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Attempt;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.GameStatus;

public class Game {
    private static final int MAX_ALLOWED_ATTEMPTS = 50;

    private final UUID id;
    private final UUID playerId;
    private final UUID dailyChallengeId;
    private final LocalDateTime startedDate;
    private GameStatus status;
    private final List<Attempt> attempts;

    public Game (UUID playerId, UUID dailyChallengeId, LocalDateTime startedDate){
        this(UUID.randomUUID(), playerId, dailyChallengeId, startedDate, GameStatus.IN_PROGRESS, new ArrayList<>());
    }

    public Game(UUID id, UUID playerId, UUID dailyChallengeId, LocalDateTime startedDate, GameStatus status, List<Attempt> attempts){
        validateState(id, playerId, dailyChallengeId, startedDate, status, attempts);
        this.id = id;
        this.playerId = playerId;
        this.dailyChallengeId = dailyChallengeId;
        this.startedDate = startedDate;
        this.status = status;
        this.attempts = new ArrayList<>(attempts);
    }

    public void addAttempt(Attempt attempt){
        if(!GameStatus.IN_PROGRESS.equals(status)){
            throw new InvalidGameException("Game must be in progress to add attempts");
        }

        if(attempt.date().isBefore(startedDate)){
            throw new InvalidGameException("Attempt date cannot be before started date game");
        }

        if(attempts.size() >= MAX_ALLOWED_ATTEMPTS){
            throw new InvalidGameException("Game has reached the maximum number of attempts");
        }

        attempts.add(attempt);

        if (attempt.isCorrect() || MAX_ALLOWED_ATTEMPTS == attempts.size()){
            this.status = GameStatus.COMPLETED;           
        }
    }

    public UUID getId(){
        return id;
    }

    public UUID getPlayerId(){
        return playerId;
    }

    public UUID getDailyChallengeId(){
        return dailyChallengeId;
    }

    public LocalDateTime getStartedDate(){
        return startedDate;
    }

    public GameStatus getStatus(){
        return status;
    }

    public List<Attempt> getAttempts(){
        return Collections.unmodifiableList(attempts);
    }

    public boolean isWon(){
        return GameStatus.COMPLETED.equals(status) && !attempts.isEmpty() && attempts.get(attempts.size() - 1).isCorrect();
    }

    public boolean isFailed(){
        return GameStatus.COMPLETED.equals(status) && !attempts.isEmpty() && !attempts.get(attempts.size() - 1).isCorrect();
    }

    private void validateState(UUID id, UUID playerId, UUID dailyChallengeId, LocalDateTime startedDate, GameStatus status, List<Attempt> attempts){
        if(Objects.isNull(id)){
            throw new InvalidGameException("Id cannot be null");
        }

        if (Objects.isNull(playerId)){
            throw new InvalidGameException("Player id cannot be null");
        }

        if(Objects.isNull(dailyChallengeId)){
            throw new InvalidGameException("Daily challenge id cannot be null");
        }

        if(Objects.isNull(startedDate)){
            throw new InvalidGameException("Game date cannot be null");
        }

        if(Objects.isNull(status)){
            throw new InvalidGameException("Game status cannot be null");
        }

        if(Objects.isNull(attempts)){
            throw new InvalidGameException("Attempts cannot be null");
        }

        if(attempts.size() > MAX_ALLOWED_ATTEMPTS){
            throw new InvalidGameException("Game has reached the maximum number of attempts");
        }

        for(Attempt attempt: attempts){
            if (attempt.date().isBefore(startedDate)){
                throw new InvalidGameException("Attempt date cannot be before started date game");
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Game other = (Game) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Game [id=" + id + ", playerId=" + playerId + ", dailyChallengeId=" + dailyChallengeId + ", startedDate=" + startedDate + ", status=" + status + ", attempts=" + attempts + "]";
    }
}
