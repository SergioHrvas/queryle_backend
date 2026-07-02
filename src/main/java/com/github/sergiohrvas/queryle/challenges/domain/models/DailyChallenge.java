package com.github.sergiohrvas.queryle.challenges.domain.models;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import com.github.sergiohrvas.queryle.challenges.domain.exceptions.InvalidDailyChallengeException;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Difficulty;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Query;

public class DailyChallenge {
    private final UUID id;
    private final String formulation;
    private final Query query;
    private final Difficulty difficulty;
    private final LocalDate publicationDate;
    private final UUID dataContextId;

    public DailyChallenge(String formulation, Query query, Difficulty difficulty, LocalDate publicationDate, UUID dataContextId) {
        this(UUID.randomUUID(), formulation, query, difficulty, publicationDate, dataContextId);
    }

    public DailyChallenge(UUID id, String formulation, Query query, Difficulty difficulty, LocalDate publicationDate, UUID dataContextId) {
        validateState(id, formulation, query, difficulty, publicationDate, dataContextId);
        this.id = id;
        this.formulation = formulation;
        this.query = query;
        this.difficulty = difficulty;
        this.publicationDate = publicationDate;
        this.dataContextId = dataContextId;
    }

    private void validateState(UUID id, String formulation, Query query, Difficulty difficulty, LocalDate publicationDate, UUID dataContextId) {
        if(Objects.isNull(id)){
            throw new InvalidDailyChallengeException("Challenge ID cannot be null");
        }

        if(Objects.isNull(formulation) || formulation.trim().isEmpty()){
            throw new InvalidDailyChallengeException("Formulation cannot be null or empty");
        }

        if(Objects.isNull(query)){
            throw new InvalidDailyChallengeException("Query cannot be null");
        }

        if(Objects.isNull(difficulty)){
            throw new InvalidDailyChallengeException("Difficulty cannot be null");
        }

        if(Objects.isNull(publicationDate)){
            throw new InvalidDailyChallengeException("Publication date cannot be null");
        }

        if(Objects.isNull(dataContextId)){
            throw new InvalidDailyChallengeException("Data context id cannot be null"); 
        }
    }

    public UUID getId(){
        return id;
    }

    public String getFormulation(){
        return formulation;
    }

    public Query getQuery(){
        return query;
    }

    public LocalDate getPublicationDate(){
        return publicationDate;
    }

    public Difficulty getDifficulty(){
        return difficulty;
    }

    public UUID getDataContextId(){
        return dataContextId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DailyChallenge that = (DailyChallenge) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "DailyChallenge [id=" + id + ", formulation=" + formulation + ", query=" + query + ", difficulty=" + difficulty + ", publicationDate=" + publicationDate + ", dataContextId=" + dataContextId + "]";
    }
}
