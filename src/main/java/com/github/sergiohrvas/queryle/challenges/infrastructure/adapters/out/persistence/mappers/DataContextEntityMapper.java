package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.mappers;

import java.util.Objects;

import com.github.sergiohrvas.queryle.challenges.domain.models.DataContext;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.entities.DataContextEntity;

public class DataContextEntityMapper {
    
    public static DataContextEntity toEntity(DataContext dataContext) {
        if(Objects.isNull(dataContext)) {
            throw new IllegalArgumentException("Data context cannot be null");
        }

        return new DataContextEntity(
            dataContext.getId(), 
            dataContext.getName(), 
            dataContext.getDescription(), 
            dataContext.getSeedScript()
        );
    }

    public static DataContext toDomain(DataContextEntity dataContextEntity) {
        if(Objects.isNull(dataContextEntity)) {
            throw new IllegalArgumentException("Data context entity cannot be null");
        }

        return new DataContext(
            dataContextEntity.getId(),
            dataContextEntity.getName(),
            dataContextEntity.getDescription(),
            dataContextEntity.getSeedScript()
        );
    }
}
