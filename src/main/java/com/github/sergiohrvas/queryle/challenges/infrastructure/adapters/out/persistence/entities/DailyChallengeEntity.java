package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.entities;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "daily_challenges")
public class DailyChallengeEntity {
    @Id
    private UUID id;

    @Column(name = "formulation", nullable = false)
    private String formulation;

    @Column(name = "query", nullable = false)
    private String query;

    @Column(name = "difficulty", nullable = false)
    private String difficulty;

    @Column(name = "publication_date", nullable = false)
    private LocalDate publicationDate;

    @Column(name = "data_context_id", nullable = false)
    private UUID dataContextId;

    @Column(name = "sequence", nullable = false)
    private int sequence;
    
    public DailyChallengeEntity(UUID id, String formulation, String query, String difficulty, LocalDate publicationDate, UUID dataContextId, int sequence) {
        this.id = id;
        this.formulation = formulation;
        this.query = query;
        this.difficulty = difficulty;
        this.publicationDate = publicationDate;
        this.dataContextId = dataContextId;
        this.sequence = sequence;
    }

    protected DailyChallengeEntity() {}

    public UUID getId() {
        return id;
    }

    public String getFormulation() {
        return formulation;
    }

    public String getQuery() {
        return query;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }
    
    public UUID getDataContextId() {
        return dataContextId;
    }

    public int getSequence() {
        return sequence;
    }
}
