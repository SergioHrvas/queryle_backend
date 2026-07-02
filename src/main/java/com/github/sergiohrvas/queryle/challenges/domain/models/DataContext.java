package com.github.sergiohrvas.queryle.challenges.domain.models;

import java.util.Objects;
import java.util.UUID;

import com.github.sergiohrvas.queryle.challenges.domain.exceptions.InvalidDataContextException;

public class DataContext {

    private final UUID id;
    private final String name;
    private final String description;
    private final String seedScript;

    public DataContext(String name, String description, String seedScript) {
        this(UUID.randomUUID(), name, description, seedScript);
    }

    public DataContext(UUID id, String name, String description, String seedScript) {
        validateState(id, name, description, seedScript);
        this.id = id;
        this.name = name;
        this.description = description;
        this.seedScript = seedScript;
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

    public String getSeedScript() {
        return seedScript;
    }

    private void validateState(UUID id, String name, String description, String seedScript) {
        if(Objects.isNull(id)){
            throw new InvalidDataContextException("Id cannot be null");
        }
        if(Objects.isNull(name)){
            throw new InvalidDataContextException("Name cannot be null");
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DataContext other = (DataContext) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return "DataContext [id=" + id + ", name=" + name + ", description=" + description + ", seedScript=" + seedScript + "]";
    }
}
