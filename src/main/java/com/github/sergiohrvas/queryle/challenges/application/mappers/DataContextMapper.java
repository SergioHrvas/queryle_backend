package com.github.sergiohrvas.queryle.challenges.application.mappers;

import java.util.Objects;

import com.github.sergiohrvas.queryle.challenges.application.dtos.DataContextResponseDTO;
import com.github.sergiohrvas.queryle.challenges.domain.models.DataContext;

public class DataContextMapper {
    public static DataContextResponseDTO toDTO(DataContext dataContext) {
        if (Objects.isNull(dataContext)) return null;

        return new DataContextResponseDTO(
            dataContext.getId(),
            dataContext.getName(),
            dataContext.getDescription(),
            dataContext.getSeedScript()
        );
    }
}
