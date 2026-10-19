package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.datacontext.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

    @Column(name = "schema", nullable = false, columnDefinition = "TEXT")
    private String schema;

    @Column(name = "public_dataset", nullable = false, columnDefinition = "TEXT")
    private String publicDataset;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
        name = "data_context_private_datasets",
        joinColumns = @JoinColumn(name = "data_context_id")
    )
    @Column(
        name = "data_fixture",
        nullable = false,
        columnDefinition = "TEXT"
    )
    private List<String> privateDatasets = new ArrayList<>();

    public DataContextEntity(
        UUID id, 
        String name, 
        String description, 
        String schema, 
        String publicDataset, 
        List<String> privateDatasets
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.schema = schema;
        this.publicDataset = publicDataset;
        this.privateDatasets = new ArrayList<>(privateDatasets);
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

    public String getSchema() {
        return schema;
    }

    public String getPublicDataset() {
        return publicDataset   ;
    }
    
    public List<String> getPrivateDatasets() {
        return List.copyOf(privateDatasets);
    }
}
