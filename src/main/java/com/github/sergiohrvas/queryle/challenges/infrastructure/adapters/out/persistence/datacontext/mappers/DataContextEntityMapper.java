package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.datacontext.mappers;

import java.util.List;
import java.util.Objects;

import com.github.sergiohrvas.queryle.challenges.domain.models.DataContext;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Dataset;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Schema;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.datacontext.entities.DataContextEntity;

public class DataContextEntityMapper {
    
    public static DataContextEntity toEntity(DataContext dataContext) {
        if(Objects.isNull(dataContext)) {
            throw new IllegalArgumentException("Data context cannot be null");
        }
        
        List<String> privateDatasets = dataContext.getPrivateDatasets()
            .stream()
            .map(Dataset::dataFixture)
            .toList();
            
        return new DataContextEntity(
            dataContext.getId(), 
            dataContext.getName(), 
            dataContext.getDescription(), 
            dataContext.getSchema().schema(),
            dataContext.getPublicDataset().dataFixture(),
            privateDatasets
        );
    }

    public static DataContext toDomain(DataContextEntity dataContextEntity) {
        if(Objects.isNull(dataContextEntity)) {
            throw new IllegalArgumentException("Data context entity cannot be null");
        }

        List<Dataset> privateDatasets = dataContextEntity.getPrivateDatasets()
            .stream()
            .map(Dataset::new)
            .toList();

        return new DataContext(
            dataContextEntity.getId(),
            dataContextEntity.getName(),
            dataContextEntity.getDescription(),
            new Schema(
                dataContextEntity.getSchema()
            ),
            new Dataset(
                dataContextEntity.getPublicDataset()
            ),
            privateDatasets
        );
    }
}
