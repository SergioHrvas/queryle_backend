package com.github.sergiohrvas.queryle.challenges.domain.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import com.github.sergiohrvas.queryle.challenges.domain.events.GameCompletedEvent;
import com.github.sergiohrvas.queryle.challenges.domain.exceptions.InvalidGameException;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Attempt;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.GameStatus;

public class Game {
    private static final int MAX_ALLOWED_ATTEMPTS = 50;

    private final UUID id;
    private final UUID playerId;
    private final UUID dailyChallengeId;
    private final LocalDateTime startedAt;
    private GameStatus status;
    private final List<Attempt> attempts;

    private final List<GameCompletedEvent> domainEvents = new ArrayList<>();

    public Game (UUID playerId, UUID dailyChallengeId, LocalDateTime startedAt){
        this(UUID.randomUUID(), playerId, dailyChallengeId, startedAt, GameStatus.IN_PROGRESS, new ArrayList<>());
    }

    public Game(UUID id, UUID playerId, UUID dailyChallengeId, LocalDateTime startedAt, GameStatus status, List<Attempt> attempts){
        validateState(id, playerId, dailyChallengeId, startedAt, status, attempts);
        this.id = id;
        this.playerId = playerId;
        this.dailyChallengeId = dailyChallengeId;
        this.startedAt = startedAt;
        this.status = status;
        this.attempts = new ArrayList<>(attempts);
    }

    public void addAttempt(Attempt attempt){
        validateCanAddAttempt(attempt);        
        attempts.add(attempt);
        updateStatus(attempt);
        if (this.isCompleted()) {
            domainEvents.add(new GameCompletedEvent(
                this.playerId,
                this.dailyChallengeId,
                this.isWon()
            ));
        }
    }

    private void updateStatus(Attempt attempt){
        if (attempt.isCorrect()) {
            status = GameStatus.COMPLETED_WON;
            return;
        }
        
        if (attempts.size() == MAX_ALLOWED_ATTEMPTS) {
            status = GameStatus.COMPLETED_FAILED;  
        }
    }

    private void validateCanAddAttempt(Attempt attempt){
        if(Objects.isNull(attempt)){
            throw new InvalidGameException("Attempt cannot be null");
        }

        if(status != GameStatus.IN_PROGRESS){
            throw new InvalidGameException("Game must be in progress to add attempts");
        }

        if(attempt.submittedAt().isBefore(startedAt)){
            throw new InvalidGameException("Submission date cannot be before started date game");
        }

        if (!attempts.isEmpty()) {
            Attempt lastAttempt = attempts.get(attempts.size()-1);
            if (attempt.submittedAt().isBefore(lastAttempt.submittedAt())) {
                throw new InvalidGameException("Submission date cannot be before last attempt submission date");
            }
        }

        if(attempts.size() >= MAX_ALLOWED_ATTEMPTS){
            throw new InvalidGameException("Game has reached the maximum number of attempts");
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

    public LocalDateTime getStartedAt(){
        return startedAt;
    }

    public GameStatus getStatus(){
        return status;
    }

    public List<Attempt> getAttempts(){
        return Collections.unmodifiableList(attempts);
    }

    public boolean isWon(){
        return status == GameStatus.COMPLETED_WON;
    }

    public boolean isFailed(){
        return status == GameStatus.COMPLETED_FAILED;
    }

    public boolean isCompleted(){
        return isWon() || isFailed();
    }

    private void validateState(UUID id, UUID playerId, UUID dailyChallengeId, LocalDateTime startedAt, GameStatus status, List<Attempt> attempts){
        if(Objects.isNull(id)){
            throw new InvalidGameException("Id cannot be null");
        }

        if (Objects.isNull(playerId)){
            throw new InvalidGameException("Player id cannot be null");
        }

        if(Objects.isNull(dailyChallengeId)){
            throw new InvalidGameException("Daily challenge id cannot be null");
        }

        if(Objects.isNull(startedAt)){
            throw new InvalidGameException("Started date cannot be null");
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

        boolean hasCorrectAttempt = false;
        Optional<Attempt> previousAttempt = Optional.empty();
        
        for(Attempt attempt: attempts){
            if (Objects.isNull(attempt)) {
                throw new InvalidGameException("Attempts cannot contain null values");
            }
            if (attempt.submittedAt().isBefore(startedAt)){
                throw new InvalidGameException("Submission date cannot be before started date game");
            }
            if (previousAttempt.isPresent() && attempt.submittedAt().isBefore(previousAttempt.get().submittedAt())) {
                throw new InvalidGameException("Submission date cannot be before previous attempt submission date");
            }
            if (hasCorrectAttempt) {
                throw new InvalidGameException("Game cannot have an attempt after a correct attempt");
            }
            previousAttempt = Optional.of(attempt);
            if (attempt.isCorrect()) {
                hasCorrectAttempt = true;
            }
        }


        if(hasCorrectAttempt && status != GameStatus.COMPLETED_WON){
            throw new InvalidGameException("Game status must be COMPLETED_WON if there is a correct attempt");
        }

        if(status == GameStatus.COMPLETED_FAILED && (attempts.size() < MAX_ALLOWED_ATTEMPTS || hasCorrectAttempt)){
            throw new InvalidGameException("A failed game must have the maximum number of attempts and no correct attempts");
        }

        if(status == GameStatus.COMPLETED_WON && !hasCorrectAttempt){
            throw new InvalidGameException("A won game must have at least one correct attempt");
        }

        if(status == GameStatus.IN_PROGRESS && hasCorrectAttempt){
            throw new InvalidGameException("An in-progress game cannot have correct attempts");
        }

        if (status == GameStatus.IN_PROGRESS && attempts.size() == MAX_ALLOWED_ATTEMPTS && !hasCorrectAttempt) {
            throw new InvalidGameException("An in-progress game cannot have the maximum number of failed attempts");
        }
    }
    
    public List<GameCompletedEvent> pullDomainEvents() {
        List<GameCompletedEvent> events = new ArrayList<>(domainEvents);
        domainEvents.clear();
        return events;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (Objects.isNull(obj) || getClass() != obj.getClass()) return false;
        Game other = (Game) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Game [id=" + id + ", playerId=" + playerId + ", dailyChallengeId=" + dailyChallengeId + ", startedAt=" + startedAt + ", status=" + status + ", attempts=" + attempts + "]";
    }
}
