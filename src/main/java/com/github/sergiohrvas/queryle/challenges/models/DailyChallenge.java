package com.github.sergiohrvas.queryle.challenges.models;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import com.github.sergiohrvas.queryle.challenges.domain.exceptions.InvalidDailyChallengeException;
import com.github.sergiohrvas.queryle.challenges.models.valueobjects.Difficulty;
import com.github.sergiohrvas.queryle.challenges.models.valueobjects.Query;

public class DailyChallenge {
    private UUID id;
    private String formulation;
    private Query query;
    private Difficulty difficulty;
    private LocalDate publicationDate;
    private UUID dataContext;

    public DailyChallenge(String formulation, Query query, Difficulty difficulty, LocalDate publicationDate, UUID dataContext) {
        this(UUID.randomUUID(), formulation, query, difficulty, publicationDate, dataContext);
    }

    public DailyChallenge(UUID id, String formulation, Query query, Difficulty difficulty, LocalDate publicationDate, UUID dataContext) {
        validateState(id, formulation, query, difficulty, publicationDate, dataContext);
        this.id = id;
        this.formulation = formulation;
        this.query = query;
        this.difficulty = difficulty;
        this.publicationDate = publicationDate;
        this.dataContext = dataContext;
    }

    private void validateState(UUID id, String formulation, Query query, Difficulty difficulty, LocalDate publicationDate, UUID dataContext) {
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

        if(Objects.isNull(dataContext)){
            throw new InvalidDailyChallengeException("Data context cannot be null"); 
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

    public UUID getDataContext(){
        return dataContext;
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
}
