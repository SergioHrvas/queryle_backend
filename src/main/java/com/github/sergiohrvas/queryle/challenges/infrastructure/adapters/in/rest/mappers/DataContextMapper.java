package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.mappers;

import java.util.Objects;

import com.github.sergiohrvas.queryle.challenges.domain.models.DataContext;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response.DataContextResponseDTO;

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
