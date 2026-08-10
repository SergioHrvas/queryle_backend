package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.entities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "data_contexts")
public class DataContextEntity {
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false, length = 1000)
    private String description;

    @Column(name = "seed_script", nullable = false, columnDefinition = "TEXT")
    private String seedScript;

    public DataContextEntity(UUID id, String name, String description, String seedScript) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.seedScript = seedScript;
    }
    
    protected DataContextEntity() {}

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSeedScript() {
        return seedScript;
    }
}
