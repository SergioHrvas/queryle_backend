package com.github.sergiohrvas.queryle.challenges.domain.models;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.github.sergiohrvas.queryle.challenges.domain.exceptions.InvalidDataContextException;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Schema;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Dataset;

public class DataContext {

    private final UUID id;
    private final String name;
    private final String description;
    private final Schema schema;
    private final Dataset publicDataset;
    private final List<Dataset> privateDatasets;

    public DataContext(String name, String description, Schema schema, Dataset publicDataset, List<Dataset> privateDatasets) {
        this(UUID.randomUUID(), name, description, schema, publicDataset, privateDatasets);
    }

    public DataContext(UUID id, String name, String description, Schema schema, Dataset publicDataset, List<Dataset> privateDatasets) {
        validateState(id, name, description, schema, publicDataset, privateDatasets);
        this.id = id;
        this.name = name;
        this.description = description;
        this.schema = schema;
        this.publicDataset = publicDataset;
        this.privateDatasets = List.copyOf(privateDatasets);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Schema getSchema() {
        return schema;
    }

    public Dataset getPublicDataset() {
        return publicDataset;
    }

    public List<Dataset> getPrivateDatasets() {
        return privateDatasets;
    }

    private void validateState(UUID id, String name, String description, Schema schema, Dataset publicDataset, List<Dataset> privateDatasets) {
        if(Objects.isNull(id)){
            throw new InvalidDataContextException("Id cannot be null");
        }
        if(Objects.isNull(name)){
            throw new InvalidDataContextException("Name cannot be null");
        }
        if(Objects.isNull(description)){
            throw new InvalidDataContextException("Description cannot be null");
        }
        if(Objects.isNull(schema)){
            throw new InvalidDataContextException("Schema cannot be null");
        }
        if(Objects.isNull(publicDataset)){
            throw new InvalidDataContextException("Public dataset cannot be null");
        }
        if(Objects.isNull(privateDatasets)){
            throw new InvalidDataContextException("Private datasets cannot be null");
        }
        if(name.trim().isBlank()){
            throw new InvalidDataContextException("Name cannot be empty");
        }
        if(description.trim().isBlank()){
            throw new InvalidDataContextException("Description cannot be empty");
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (Objects.isNull(obj) || getClass() != obj.getClass()) return false;
        DataContext other = (DataContext) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return "DataContext [id=" + id + ", name=" + name + ", description=" + description + ", schema=" + schema + ", publicDataset=" + publicDataset + ", privateDatasets=" + privateDatasets + "]";
    }
}
